/*
 * Copyright (c) 2014, Absolute Performance, Inc. http://www.absolute-performance.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package ublu.command;

import ublu.util.ArgArray;
import ublu.util.DataSink;
import ublu.util.Tuple;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.PrintObject;
import com.ibm.as400.access.PrintParameterList;
import com.ibm.as400.access.Printer;
import com.ibm.as400.access.RequestNotSupportedException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Get and set printer attributes
 *
 * @author jwoehr
 */
public class CmdPrinter extends Command {

    {
        setNameAndDescription("printer",
                "/4? [-as400 @as400] [--,-printer ~@printer] [-to @var] [-get ~@attribute] | [[-instance] | [-set ~@attribute value]] ~@printername ~@system ~@user ~@password : instance as400 printer");
    }

    private enum OPERATIONS {

        /**
         * Put instance of printer
         */
        INSTANCE,
        /**
         * get attrib
         */
        GET,
        /**
         * set attrib
         */
        SET,
        /**
         * do nothing
         */
        NOOP
    }

    /**
     * Get and set printer attributes
     *
     * @param argArray arguments in interpreter buffer
     * @return what's left of arguments
     */
    public ArgArray printer(ArgArray argArray) {
        OPERATIONS operation = OPERATIONS.INSTANCE;
        String attribute = "";
        Tuple printerTuple = null;
        PrintParameterList ppl = new PrintParameterList();
        while (argArray.hasDashCommand()) {
            String dashCommand = argArray.parseDashCommand();
            switch (dashCommand) {
                case "-to":
                    String destName = argArray.next();
                    setDataDest(DataSink.fromSinkName(destName));
                    break;
                case "-get":
                    operation = OPERATIONS.GET;
                    attribute = argArray.nextMaybeQuotationTuplePopString();
                    break;
                case "-set":
                    operation = OPERATIONS.SET;
                    attribute = argArray.nextMaybeQuotationTuplePopString();
                    switch (attribute) {
                        case "CHANGES":
                            ppl.setParameter(attribToInt(attribute), argArray.next());
                            break;
                        case "DRWRSEP":
                            ppl.setParameter(attribToInt(attribute), argArray.nextInt());
                            break;
                        case "FILESEP":
                            ppl.setParameter(attribToInt(attribute), argArray.nextInt());
                            break;
                        case "FORMTYPE":
                            ppl.setParameter(attribToInt(attribute), argArray.next());
                            break;
                        case "OUTPUT_QUEUE":
                            ppl.setParameter(attribToInt(attribute), argArray.next());
                            break;
                        case "DESCRIPTION":
                            ppl.setParameter(attribToInt(attribute), argArray.next());
                            break;
                        default:
                            getLogger().log(Level.SEVERE, "Unknown attribute {0} in {1}", new Object[]{attribute, getNameAndDescription()});
                            setCommandResult(COMMANDRESULT.FAILURE);
                    }
                    break;
                case "-as400":
                    setAs400(getAS400Tuple(argArray.next()));
                    break;
                case "--":
                case "-printer":
                    printerTuple = argArray.nextTupleOrPop();
                    break;
                default:
                    unknownDashCommand(dashCommand);
            }
        }
        if (havingUnknownDashCommand() || getCommandResult() == COMMANDRESULT.FAILURE) {
            setCommandResult(COMMANDRESULT.FAILURE);
        } else {
            Printer printer = null;
            String printerName = null;
            if (printerTuple != null) {
                Object o = printerTuple.getValue();
                if (o instanceof Printer) {
                    printer = Printer.class.cast(o);
                } else {
                    getLogger().log(Level.SEVERE, "Tuple value is not a Printer object in  {0}", getNameAndDescription());
                    setCommandResult(COMMANDRESULT.FAILURE);
                }
            } else {
                if (argArray.size() < 1) {
                    logArgArrayTooShortError(argArray);
                    setCommandResult(COMMANDRESULT.FAILURE);
                } else {
                    printerName = argArray.nextMaybeQuotationTuplePopString();
                }
            }
            if (printer == null && getAs400() == null) {
                try {
                    if (argArray.size() < 3) {
                        logArgArrayTooShortError(argArray);
                        setCommandResult(COMMANDRESULT.FAILURE);
                    } else {
                        setAs400FromArgs(argArray);
                    }
                } catch (PropertyVetoException ex) {
                    getLogger().log(Level.SEVERE, "Couldn't instance AS400 in " + getNameAndDescription(), ex);
                    setCommandResult(COMMANDRESULT.FAILURE);
                }
            }
            if (printer == null && getAs400() != null) {
                printer = new Printer(getAs400(), printerName);
            }
            if (printer != null) {
                switch (operation) {

                    case INSTANCE:
                        try {
                            put(printer);
                        } catch (SQLException | IOException | AS400SecurityException | ErrorCompletingRequestException | InterruptedException | ObjectDoesNotExistException | RequestNotSupportedException ex) {
                            getLogger().log(Level.SEVERE, "Exception putting Printer object in " + getNameAndDescription(), ex);
                            setCommandResult(COMMANDRESULT.FAILURE);
                        }
                        break;
                    case GET:
                        try {
                            put(printer.getStringAttribute(Integer.parseInt(attribute)));
                        } catch (AS400SecurityException | SQLException | ObjectDoesNotExistException | IOException | InterruptedException | RequestNotSupportedException | ErrorCompletingRequestException ex) {
                            getLogger().log(Level.SEVERE, null, ex);
                            setCommandResult(COMMANDRESULT.FAILURE);
                        }
                        break;
                    case SET:
                        try {
                            printer.setAttributes(ppl);
                        } catch (AS400SecurityException | IOException | InterruptedException | ErrorCompletingRequestException ex) {
                            getLogger().log(Level.SEVERE, null, ex);
                            setCommandResult(COMMANDRESULT.FAILURE);
                        }
                        break;
                    case NOOP:
                        break;
                }
            }
        }
        return argArray;
    }

    private Integer attribToInt(String attrib) {
        Integer intval = null;
        switch (attrib) {
            case "CHANGES":
                intval = new Integer(PrintObject.ATTR_CHANGES);
                break;
            case "DRWRSEP":
                intval = new Integer(PrintObject.ATTR_DRWRSEP);
                break;
            case "FILESEP":
                intval = new Integer(PrintObject.ATTR_FILESEP);
                break;
            case "FORMTYPE":
                intval = new Integer(PrintObject.ATTR_FORMTYPE);
                break;
            case "OUTPUT_QUEUE":
                intval = new Integer(PrintObject.ATTR_OUTPUT_QUEUE);
                break;
            case "DESCRIPTION":
                intval = new Integer(PrintObject.ATTR_DESCRIPTION);
                break;
            default:
                getLogger().log(Level.SEVERE, "Unknown attribute {0} in {1}", new Object[]{attrib, getNameAndDescription()});
                setCommandResult(COMMANDRESULT.FAILURE);
        }
        return intval;
    }

    @Override
    public ArgArray cmd(ArgArray args) {
        reinit();
        return printer(args);
    }

    @Override
    public COMMANDRESULT getResult() {
        return getCommandResult();
    }
}