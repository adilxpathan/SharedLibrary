package com.hexaware.service
class Codecheckout implements Serializable{
Script mainScript
Map specs

  def Codecheckout(Script mainScript, Map specs){
  this.mainScript = mainScript
  this.specs = specs
  }
  def checkOutFunc(Map specs){
    mainScript.checkout([$class: 'GitSCM',
    branches: [[name: specs.branch]],
    extensions: [],
    userRemoteConfigs: [[url: specs.repo ]]
    ])
  }
}
