package sample.Model.SIP;

import sample.Model.Net.ClientHandler;

/**
 * Created by Anton on 2016-10-11.
 */
public class SipHandler {
    private SipState currentState;

    public SipHandler(){
        currentState = new StateIdling();
    }

    public String getState(){
        return currentState.getStateName();
    }

    public void processNextState(String message, ClientHandler client){
        SipEvent evt = parseMessage(message);
        if(evt!=null){
            switch(evt){
                case INVITE_RECEIVED:
                    try{
                        currentState = currentState.receivedInviteReceived(client, Integer.parseInt(message.split(" ")[1]));
                    }catch(Exception e){
                        currentState = currentState.receivedError(client);
                    }
                    break;
                case TRO_ACK_RECEIVED:
                    currentState = currentState.receivedTROAck(client);
                    break;
                case TRO_RECEIVED:
                    try{
                        currentState = currentState.receivedTRO(client, Integer.parseInt(message.split(" ")[1]));
                    }catch(Exception e){
                        currentState = currentState.receivedError(client);
                    }
                    break;
                case BYE_RECEIVED:
                    currentState = currentState.receivedByeReceived(client);
                    break;
                case BYE_OK_RECEIVED:
                    currentState = currentState.receivedByeOk(client);
                    break;
                case BUSY:
                    currentState = currentState.receivedBusy(client);
                    break;
                case ALIVE:
                    //do nothing. this is simply for testing if socket communication is open.
                    break;
                default:
                    currentState = currentState.receivedError(client);
                    break;
            }
        }else{
            currentState = currentState.receivedBye(client);
        }

    }

    private SipHandler.SipEvent parseMessage(String msg){
        String[] split = msg.split(" ");

        switch(split[0]){
            case "INVITE":
                return SipHandler.SipEvent.INVITE_RECEIVED;
            case "TRO":
                return SipHandler.SipEvent.TRO_RECEIVED;
            case "TRO_ACK":
                return SipHandler.SipEvent.TRO_ACK_RECEIVED;
            case "BYE":
                return SipHandler.SipEvent.BYE_RECEIVED;
            case "BYE_OK":
                return SipHandler.SipEvent.BYE_OK_RECEIVED;
            case "BUSY":
                return SipEvent.BUSY;
            case "ALIVE":
                return SipEvent.ALIVE;
            default:
                return null;
        }
    }

    public void invokeError(ClientHandler client){
        currentState = currentState.receivedError(client);
    }

    public void invokeBye(ClientHandler client){
        currentState = currentState.receivedBye(client);
    }

    public void invokeAcceptInvite(ClientHandler client){
        currentState = currentState.receivedInviteAccepted(client);
    }

    public void invokeInvite(ClientHandler client){
        currentState = currentState.receivedInvite(client);
    }

    public enum SipEvent{
        INVITE_SENT,
        INVITE_RECEIVED,
        TRO_ACK_RECEIVED,
        TRO_RECEIVED,
        BYE_RECEIVED,
        BYE_SENT_RECEIVED,
        BYE_OK_RECEIVED,
        BUSY,
        ALIVE
    }
}
