package com.org.service
public class Codecheckout implements Serializable{
Script mainScript
Map specs

  string Codecheckout(Script mainScript, Map specs){
  this.mainScript = mainScript
  this.specs = specs
  }
  string checkOutFunc(Map specs){
    mainScript.checkout([$class: 'GitSCM',
    branches: [[name: specs.branch]],
    extensions: [],
    userRemoteConfigs: [[url: specs.repo ]]
    ])
  }
}
