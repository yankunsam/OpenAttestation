#!/bin/bash
#run TrustAgentLinuxInstaller-*.bin with given parameters

oat_server_ip=${oat_server_ip:-""}
oat_server_port=${oat_server_port:-""}
pca_user=${pca_user:-""}
pca_passwd=${pca_passwd:-""}
tpm_passwd=${tpm_passwd:-""}
tagent_bin_path=${tagent_bin_path:-""}

example()
{
    echo $"$0: Usage: $0 --bin_path <Path to TrustAgentLinuxInstaller-*.bin> --ip <IP of oatserver> --port <PORT of oatserver> --pca_user <PCA User name> --pca_passwd <PCA passwd> --tpm_passwd <TPM passwd>}"
}

if [ $# -ne 12 ]; then
    example
    exit 1
fi

while [ $# -gt 1 ]; do
  case $1 in
    --bin_path)
        tagent_bin_path=$2
        shift 2
        ;;
    --ip)
        oat_server_ip=$2
        shift 2
        ;;
    --port)
        oat_server_port=$2
        shift 2
        ;;
    --pca_user)
        pca_user=$2
        shift 2
        ;;
    --pca_passwd)
        pca_passwd=$2
        shift 2
        ;;
    --tpm_passwd)
        tpm_passwd=$2
        shift 2
        ;;
    *)
        example
        exit 1;;
  esac
done


bash $tagent_bin_path $tpm_passwd <<EOF
$oat_server_ip
$oat_server_port
$pca_user
$pca_passwd
EOF
