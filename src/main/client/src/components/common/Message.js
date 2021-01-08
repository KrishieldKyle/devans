import React, { Component } from 'react'
import PropTypes from 'prop-types';
import "../../assets/css/common/Message.css";
import { connect } from 'react-redux';

import { closeMessage } from "../../actions/messageActions";

export class Message extends Component {

    constructor() {
        super()
        this.state = {
            message: {},
            messageTimer: clearTimeout(),
            isSuccessCopy: false,
            messageCopy: ""
        }
        
        this.closeMessage = this.closeMessage.bind(this);
    }

    closeMessage(){
        clearTimeout(this.state.messageTimer);
        this.props.closeMessage();
    }

    componentDidUpdate(prevProps){
        
        if(prevProps.message !== this.props.message){
           
            if(this.props.message.isMessageShowing){
                this.setState({
                    message : this.props.message, 
                    messageCopy: this.props.message.message,
                    isSuccessCopy: this.props.message.isSuccess
                }, () => {
                    clearTimeout(this.state.messageTimer);
                    this.setState({
                        messageTimer : setTimeout(() => {
                            this.props.closeMessage();
                        }, 5000)
                    })
                })
            }
            else {
                this.setState({
                    message : this.props.message
                })
            }
        }
    }
    
    render() {

        const {message } = this.state.message;
        const { messageCopy, isSuccessCopy} = this.state;

        return (
            <div id="message-container" className={message ? "show-message" : "hide-message"}>
                <div id="message" className={isSuccessCopy ? "success" : "failed"}>
                    <p>{messageCopy}</p>
                        <i className="fa fa-close" onClick={this.closeMessage}></i>
                </div>
            </div>
        )
    }
}

Message.propTypes = {
    message: PropTypes.object.isRequired,
    closeMessage: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    message: state.message
})

export default connect(mapStateToProps, {closeMessage})(Message);
