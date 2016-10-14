/*
 * Copyright (c) 2015, Absolute Performance, Inc. http://www.absolute-performance.com
 * Copyright (c) 2016, Jack J. Woehr jwoehr@softwoehr.com 
 * SoftWoehr LLC PO Box 51, Golden CO 80402-0051 http://www.softwoehr.com
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
package ublu;

/**
 * Provides a date/time stamp for the build.
 *
 * <p>
 * DO NOT EDIT this file in package {@code ublu}. Edit this file as
 * {@code Ublu/stuff/_proto_Version.java}.</p>
 *
 * <p>
 * The build file for this project copies this class file to the {@code ublu}
 * package instancing the date stamp in the process. See the
 * {@code -pre-compile} target of {@code Ublu/build.xml}.</p>
 *
 * @author jwoehr
 */
public class Version {

    private Version() {
    }
    /**
     * A date/time stamp to be called up by whatever wants to know. E.g., it is
     * displayed in the interpreter startup message. The actual timestamp is
     * instanced by the the {@code -pre-compile} target of
     * {@code Ublu/build.xml}.
     * <p>
     * The code was last compiled at
     *
     * @compile.date.time@.</p>
     */
    public static String ubluVersion = "@ublu.version@";
    public static String compileDateTime = "@compile.date.time@";
}
