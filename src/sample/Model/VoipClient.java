package sample.Model;

/**
 * Created by Anton on 2016-10-11.
 */
public class VoipClient {
    private static SipState currentState;

    public VoipClient(){
        currentState = new StateIdle();

        //swithbhbhbh
        currentState = currentState.nextState("invite");
    }
}
