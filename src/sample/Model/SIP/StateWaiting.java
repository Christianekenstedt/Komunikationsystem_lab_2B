package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class StateWaiting extends SipState {

    @Override
    SipState receivedTRO(ClientHandler remote){
        remote.send("TRO_ACK");
        return new StateStreaming();
    }

    @Override
    String getStateName() {
        return null;
    }
}
