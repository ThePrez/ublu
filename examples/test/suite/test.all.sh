# test.all.sh ... run test suite from test suite directory 
# autogenerated Wed Aug 09 04:47:03 MDT 2017 by jax using command:
# gensh -to test.all.sh -path /opt/ublu/ublu.jar -optr d DEFAULTS @defaults ${ full path to defaults file }$ -optr w WORKDIR @wdir ${ Work directory for test }$ -optr i IFSPATH @ifspath ${ IFS path to test report physical file to be created }$ -optr c DESCRIPTION @description ${ description for test run pf created }$ -opts k DELWORK ${ delete all files in work dir if value is Y }$ -prelude ${ if [ "${DELWORK}" == "Y" ]; then rm -rf ${WORKDIR}/*; fi }$ -prelude ${ echo "Changing directory to $SCRIPTDIR" }$ -prelude ${ cd $SCRIPTDIR }$ ${ test.all.sh ... run test suite from test suite directory }$ ./test.all.ublu ${ test.all ( @defaults @wdir @ifspath @description ) }$

# Usage message
function usage {
echo "test.all.sh ... run test suite from test suite directory "
echo "This shell script was autogenerated Wed Aug 09 04:47:03 MDT 2017 by jax."
echo "Usage: $0 [silent] [-h] [-X...] [-Dprop=val] -d DEFAULTS -w WORKDIR -i IFSPATH -c DESCRIPTION [-k DELWORK]"
echo "	where"
echo "	-h		display this help message and exit 0"
echo "	-X xOpt		pass a -X option to the JVM (can be used many times)"
echo "	-D some.property=\"some value\"	pass a property to the JVM (can be used many times)"
echo "	-d DEFAULTS	full path to defaults file  (required option)"
echo "	-w WORKDIR	Work directory for test  (required option)"
echo "	-i IFSPATH	IFS path to test report physical file to be created  (required option)"
echo "	-c DESCRIPTION	description for test run pf created  (required option)"
echo "	-k DELWORK	delete all files in work dir if value is Y "
echo "---"
echo "If the keyword 'silent' appears ahead of all options, then included files will not echo and prompting is suppressed."
echo "Exit code is the result of execution, or 0 for -h or 2 if there is an error in processing options"
echo "This script sets \$SCRIPTDIR to the script's directory prior to executing prelude commands and Ublu invocation."
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
while getopts d:w:i:c:k:D:X:h the_opt
do
	case "$the_opt" in
		d)	DEFAULTS="$OPTARG";;
		w)	WORKDIR="$OPTARG";;
		i)	IFSPATH="$OPTARG";;
		c)	DESCRIPTION="$OPTARG";;
		k)	DELWORK="$OPTARG";;
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
if [ "${DEFAULTS}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @defaults -trim \${ ${DEFAULTS} }$ "
else
	echo "Option -d DEFAULTS is a required option but is not present."
	usage
	exit 2
fi
if [ "${WORKDIR}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @wdir -trim \${ ${WORKDIR} }$ "
else
	echo "Option -w WORKDIR is a required option but is not present."
	usage
	exit 2
fi
if [ "${IFSPATH}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @ifspath -trim \${ ${IFSPATH} }$ "
else
	echo "Option -i IFSPATH is a required option but is not present."
	usage
	exit 2
fi
if [ "${DESCRIPTION}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @description -trim \${ ${DESCRIPTION} }$ "
else
	echo "Option -c DESCRIPTION is a required option but is not present."
	usage
	exit 2
fi

SCRIPTDIR=$(CDPATH= cd "$(dirname "$0")" && pwd)

# Prelude commands to execute before invocation
if [ "${DELWORK}" == "Y" ]; then rm -rf ${WORKDIR}/*; fi
echo "Changing directory to $SCRIPTDIR"
cd $SCRIPTDIR

# Invocation
java${JVMOPTS}${JVMPROPS} -Dublu.includepath="" -jar /opt/ublu/ublu.jar ${gensh_runtime_opts} include ${SILENT}./test.all.ublu test.all \( @defaults @wdir @ifspath @description \) 
exit $?
