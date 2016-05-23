# autoreply.sh ... Find and reply to all *INQ & *NOTIFY messages in a given MSGQ 
# autogenerated Wed Mar 09 19:26:19 MST 2016 by jax using command:
# gensh -to autoreply.sh -path /opt/ublu/ublu.jar -optr s SERVER @server ${ Server }$ -optr u USER @user ${ User }$ -optr p PASSWORD @password ${ Password }$ -optr q QPATHIFS @qpathifs ${ IFS path to message queue, e.g., /QSYS.LIB/QSYSOPR.MSGQ or /QSYS.LIB/QUSRSYS.LIB/MYUSERID.MSGQ }$ ${ autoreply.sh ... Find and reply to all *INQ & *NOTIFY messages in a given MSGQ }$ /opt/ublu/examples/autoreply.ublu ${ autoreply ( @server @user @password @qpathifs ) }$

# Usage message
function usage { 
echo "autoreply.sh ... Find and reply to all *INQ & *NOTIFY messages in a given MSGQ "
echo "This shell script was autogenerated Wed Mar 09 19:26:19 MST 2016 by jax."
echo "Usage: $0 [silent] -h -s SERVER -u USER -p PASSWORD -q QPATHIFS "
echo "	where"
echo "	-h		display this help message and exit 0"
echo "	-s SERVER	Server  (required option)"
echo "	-u USER	User  (required option)"
echo "	-p PASSWORD	Password  (required option)"
echo "	-q QPATHIFS	IFS path to message queue, e.g., /QSYS.LIB/QSYSOPR.MSGQ or /QSYS.LIB/QUSRSYS.LIB/MYUSERID.MSGQ  (required option)"
echo "---"
echo "If the keyword 'silent' appears ahead of all options, then included files will not echo and prompting is suppressed."
echo "Exit code is the result of execution, or 0 for -h or 2 if there is an error in processing options"
}

#Test if user wants silent includes
if [ "$1" == "silent" ]
then
	SILENT="-silent "
	shift
else
	SILENT=""
fi

# Process options
while getopts s:u:p:q:h the_opt
do
	case "$the_opt" in
		s)	SERVER="$OPTARG";;
		u)	USER="$OPTARG";;
		p)	PASSWORD="$OPTARG";;
		q)	QPATHIFS="$OPTARG";;
		h)	usage;exit 0;;
		[?])	usage;exit 2;;

	esac
done
shift `expr ${OPTIND} - 1`
if [ $# -ne 0 ]
then
	echo "Superfluous argument(s) $*"
	usage
	exit 2
fi

# Translate options to tuple assignments
if [ "${SERVER}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @server -trim \${ ${SERVER} }$ "
else
	echo "Option -s SERVER is a required option but is not present."
	usage
	exit 2
fi
if [ "${USER}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @user -trim \${ ${USER} }$ "
else
	echo "Option -u USER is a required option but is not present."
	usage
	exit 2
fi
if [ "${PASSWORD}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @password -trim \${ ${PASSWORD} }$ "
else
	echo "Option -p PASSWORD is a required option but is not present."
	usage
	exit 2
fi
if [ "${QPATHIFS}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @qpathifs -trim \${ ${QPATHIFS} }$ "
else
	echo "Option -q QPATHIFS is a required option but is not present."
	usage
	exit 2
fi

# Invocation
java -jar /opt/ublu/ublu.jar ${gensh_runtime_opts} include ${SILENT}/opt/ublu/examples/autoreply.ublu autoreply \( @server @user @password @qpathifs \) 
exit $?