# jobnameDEQs.sh ... poll jobs of given name ; compute % of those jobs in DEQA/DEQW state ; output SystemShepherd datapoints 
# autogenerated Wed Mar 09 19:25:48 MST 2016 by jax using command:
# gensh -to jobnameDEQs.sh -path /opt/ublu/ublu.jar -optr s SERVER @server ${ Server to monitor }$ -optr u USERNAME @username ${ username }$ -optr p PASSWORD @password ${ password }$ -optr j JOB_NAME @job_name ${ Job name }$ -optr l CRITLEVEL @crit_level ${ Critical level 0 - 100 for %jobname in DEQ* state }$ ${ jobnameDEQs.sh ... poll jobs of given name ; compute % of those jobs in DEQA/DEQW state ; output SystemShepherd datapoints }$ /opt/api-java/examples/jobstate.ublu ${ JobNameDEQDPoints ( @server @username @password @job_name @crit_level ) }$

# Usage message
function usage { 
echo "jobnameDEQs.sh ... poll jobs of given name ; compute % of those jobs in DEQA/DEQW state ; output SystemShepherd datapoints "
echo "This shell script was autogenerated Wed Mar 09 19:25:48 MST 2016 by jax."
echo "Usage: $0 [silent] -h -s SERVER -u USERNAME -p PASSWORD -j JOB_NAME -l CRITLEVEL "
echo "	where"
echo "	-h		display this help message and exit 0"
echo "	-s SERVER	Server to monitor  (required option)"
echo "	-u USERNAME	username  (required option)"
echo "	-p PASSWORD	password  (required option)"
echo "	-j JOB_NAME	Job name  (required option)"
echo "	-l CRITLEVEL	Critical level 0 - 100 for %jobname in DEQ* state  (required option)"
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
while getopts s:u:p:j:l:h the_opt
do
	case "$the_opt" in
		s)	SERVER="$OPTARG";;
		u)	USERNAME="$OPTARG";;
		p)	PASSWORD="$OPTARG";;
		j)	JOB_NAME="$OPTARG";;
		l)	CRITLEVEL="$OPTARG";;
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
if [ "${USERNAME}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @username -trim \${ ${USERNAME} }$ "
else
	echo "Option -u USERNAME is a required option but is not present."
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
if [ "${JOB_NAME}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @job_name -trim \${ ${JOB_NAME} }$ "
else
	echo "Option -j JOB_NAME is a required option but is not present."
	usage
	exit 2
fi
if [ "${CRITLEVEL}" != "" ]
then
	gensh_runtime_opts="${gensh_runtime_opts}string -to @crit_level -trim \${ ${CRITLEVEL} }$ "
else
	echo "Option -l CRITLEVEL is a required option but is not present."
	usage
	exit 2
fi

# Invocation
java -jar /opt/ublu/ublu.jar ${gensh_runtime_opts} include ${SILENT}/opt/api-java/examples/jobstate.ublu JobNameDEQDPoints \( @server @username @password @job_name @crit_level \) 
exit $?