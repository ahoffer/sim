package com.sim.example.holygrail;

import com.sim.api.SimEvent;
import com.sim.api.SimReporter;

public class SpeakLine implements SimEvent {

  final String myLine;

  public SpeakLine(String myLine) {
    this.myLine = myLine;
  }

  @Override
  public void executeWith(SimReporter visitor) {
    if (visitor == null) {
      throw new RuntimeException("Visitor arg cannot be null. Use SimVisitorNoOp instead.");
    }
    visitor.report(this);
    System.out.println(myLine);
  }
}
