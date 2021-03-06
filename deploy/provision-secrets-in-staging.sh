#!/usr/bin/env bash
#---------------------------------------------------------------------------------------------------
# Provisions AWS SSM secrets in staging
#---------------------------------------------------------------------------------------------------

source $(dirname $0)/.check-requirements.sh

env ENVIRONMENTS=staging ./provision-secrets.sh $*
