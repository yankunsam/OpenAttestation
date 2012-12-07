#!/bin/bash

NUMA=0
NUMB=17
R=`mysql oat_db <<EOF
select pcr$NUMA, pcr$NUMB, machine_name from audit_log order by id desc limit 1;
EOF`
echo "$R" > 1.txt
MACHINE=`awk 'NR==2 {print $3;}' 1.txt`
PCRA=`awk 'NR==2 {print $1;}' 1.txt`
PCRB=`awk 'NR==2 {print $2;}' 1.txt`
HOST_NAME=`hostname`
PORT=8443
echo $MACHINE
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d '{"Name":"OS1","Version":"v1234","Description":"Test1"}' https://$HOST_NAME:$PORT/WLMService/resources/os -ssl3
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d '{"Name":"OEM1","Description":"Newdescription"}' https://$HOST_NAME:$PORT/WLMService/resources/oem -ssl3
INFO=`echo "{\"Name\":\"NewRHELMLE\",\"Version\":\"123\",\"OsName\":\"OS1\",\"OsVersion\":\"v1234\",\"Attestation_Type\":\"PCR\",\"MLE_Type\":\"VMM\",\"Description\":\"Test\",\"MLE_Manifests\":[{\"Name\":\"$NUMB\",\"Value\":\"$PCRB\"}]}"`
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d $INFO https://$HOST_NAME:$PORT/WLMService/resources/mles -ssl3
INFO=`echo "{\"Name\":\"New2\",\"Version\":\"123\",\"OemName\":\"OEM1\",\"Attestation_Type\":\"PCR\",\"MLE_Type\":\"BIOS\",\"Description\":\"Test1111\",\"MLE_Manifests\":[{\"Name\":\"$NUMA\",\"Value\":\"$PCRA\"}]}"`
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d $INFO https://$HOST_NAME:$PORT/WLMService/resources/mles -ssl3 > /tmp/mle
if [ "`awk '$1 ~/True/' /tmp/mle`" != "True" ];then
        curl --cacert ./certfile.cer -H "Content-Type: application/json" -X PUT -d $INFO https://$HOST_NAME:$PORT/WLMService/resources/mles -ssl3
fi
INFO=`echo "{\"HostName\":\"$MACHINE\",\"IPAddress\":\"192.168.0.1\",\"Port\":\"8080\",\"BIOS_Name\":\"New2\",\"BIOS_Version\":\"123\",\"BIOS_Oem\":\"OEM1\",\"VMM_Name\":\"NewRHELMLE\",\"VMM_Version\":\"123\",\"VMM_OSName\":\"OS1\",\"VMM_OSVersion\":\"v1234\",\"Email\":\"\",\"AddOn_Connection_String\":\"\",\"Description\":\"\"}"`
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d $INFO https://$HOST_NAME:$PORT/AttestationService/resources/host -ssl3
INFO=`echo "{\"hosts\":[\"$MACHINE\"]}"`
#INFO=`echo "{\"hosts\":[\"localhost\"]}"`
echo
curl --cacert ./certfile.cer -H "Content-Type: application/json" -X POST -d $INFO https://$HOST_NAME:$PORT/AttestationService/resources/PollHosts -ssl3 >> /tmp/Result
