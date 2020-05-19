#!/usr/bin/env bash
#---------------------------------------------------------------------------------------------------
# Provisions the complete AWS stack in staging
#---------------------------------------------------------------------------------------------------

source $(dirname $0)/.check-requirements.sh

env ENVIRONMENTS=staging ./provision-all.sh $*
