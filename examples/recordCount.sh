# recordCount.sh -- SQL count records in an IBM i physical file 
# Ublu 1.2.1+ gensh autogenerated this shell script Tue Dec 04 21:58:52 MST 2018 for jax using command:
# gensh -to recordCount.sh -path /opt/ublu/ublu.jar -optr s SYSNAME @sysname ${ System where test results reside }$ -optr z USESSL @usessl ${ T to use SSL }$ -optr u UPROF @uprof ${ User profile }$ -optr p PASSWD @passwd ${ Password }$ -optr c SCHEMA @schema ${ schema i.e library of file }$ -optr f FILENAME @filename ${ file to count rows of }$ -includepath /opt/ublu/examples/ ${ recordCount.sh -- SQL count records in an IBM i physical file }$ recordCount.ublu ${ recordCount ( @sysname @schema @filename @uprof @passwd @usessl ) }$

# Usage message
function usage {
echo "recordCount.sh -- SQL count records in an IBM i physical file "
echo "Ublu gensh autogenerated this shell script Tue Dec 04 21:58:52 MST 2018 for jax."
echo "Usage: $0 [glob] [silent] [-h] [-X...] [-Dprop=val] -s SYSNAME -z USESSL -u UPROF -p PASSWD -c SCHEMA -f FILENAME"
echo "	where"
echo "	-h		display this help message and exit 0"
echo "	-X xOpt		pass a -X option to the JVM (can be used many times)"
echo "	-D some.property=\"some value\"	pass a property to the JVM (can be used many times)"
echo "	-s SYSNAME	System where test results reside  (required option)"
echo "	-z USESSL	T to use SSL  (required option)"
echo "	-u UPROF	User profile  (required option)"
echo "	-p PASSWD	Password  (required option)"
echo "	-c SCHEMA	schema i.e library of file  (required option)"
echo "	-f FILENAME	file to count rows of  (required option)"
echo "---"
echo "If the keyword 'glob' appears ahead of all other options and arguments, only then will arguments be globbed by the executing shell (noglob default)."
echo "If the keyword 'silent' appears ahead of all options (except 'glob' if the latter is present), then included files will not echo and prompting is suppressed."
echo "Exit code is the result of execution, or 0 for -h or 2 if there is an error in processing options."
echo "This script sets \$SCRIPTDIR to the script's directory prior to executing prelude commands and Ublu invocation."
}

# Test if user wants arguments globbed - default noglob
if [ "$1" == "glob" ]
then
	set +o noglob # POSIX
	shift
else
	set -o noglob # POSIX
fi

# Test if user wants silent includes
if [ "$1" == "silent" ]
then
	SILENT="-silent "
	shift
else
	SILENT=""
fi

# Process options
while getopts s:z:u:p:c:f:D:X:h the_opt
do
	case "$the_opt" in
		s)	SYSNAME="$OPTARG";;
		z)	USESSL="$OPTARG";;
		u)	UPROF="$OPTARG";;
		p)	PASSWD="$OPTARG";;
		c)	SCHEMA="$OPTARG";;
		f)	FILENAME="$OPTARG";;
		h)	usage;exit 0;;
		D)	JVMPROPS="${JVMPROPS} -D${OPTARG}";;
		X)	JVMOPTS="${JVMOPTS} -X${OPTARG}";;
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
if [ "${SYSNAME}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @sysname -trim \${ ${SYSNAME} }$ "
else
	echo "Option -s SYSNAME is a required option but is not present."
	usage
	exit 2
fi
if [ "${USESSL}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @usessl -trim \${ ${USESSL} }$ "
else
	echo "Option -z USESSL is a required option but is not present."
	usage
	exit 2
fi
if [ "${UPROF}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @uprof -trim \${ ${UPROF} }$ "
else
	echo "Option -u UPROF is a required option but is not present."
	usage
	exit 2
fi
if [ "${PASSWD}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @passwd -trim \${ ${PASSWD} }$ "
else
	echo "Option -p PASSWD is a required option but is not present."
	usage
	exit 2
fi
if [ "${SCHEMA}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @schema -trim \${ ${SCHEMA} }$ "
else
	echo "Option -c SCHEMA is a required option but is not present."
	usage
	exit 2
fi
if [ "${FILENAME}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @filename -trim \${ ${FILENAME} }$ "
else
	echo "Option -f FILENAME is a required option but is not present."
	usage
	exit 2
fi

SCRIPTDIR=$(CDPATH= cd "$(dirname "$0")" && pwd)

# Prelude commands to execute before invocation
# No prelude commands

# Invocation
java${JVMOPTS}${JVMPROPS} -Dublu.includepath="/opt/ublu/examples/" -jar /opt/ublu/ublu.jar ${gensh_runtime_opts} include ${SILENT}recordCount.ublu recordCount \( @sysname @schema @filename @uprof @passwd @usessl \) 
exit $?