package com.sim.example.holygrail;

import com.sim.api.SimEvent;

class SpeakLine implements SimEvent {

  final String myLine;

  public SpeakLine(String myLine) {
    this.myLine = myLine;
  }

  @Override
  public void run() {
    System.out.println(myLine);
  }
}
