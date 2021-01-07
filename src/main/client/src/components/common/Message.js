import React, { Component } from 'react'
import PropTypes from 'prop-types';
import "../../assets/css/common/Message.css";
import { connect } from 'react-redux';

import { clearMessage } from "../../actions/messageActions";

export class Message extends Component {

    constructor() {
        super()
        this.state = {
            message: {},
            isSuccessCopy: false,
            isSuccessCopy2: false,
            messageCopy: "",
            messageCopy2: ""
        }
        
        this.closeMessage = this.closeMessage.bind(this);
    }

    closeMessage(){
        this.setState(
            {
                messageCopy2: this.props.message.message,
                isSuccessCopy2: this.props.message.isSuccess
            }, () => {
            this.props.clearMessage();
        })
    }

    componentDidUpdate(prevProps){
        console.log("prevProps.message",prevProps.message);
        console.log("this.props.message",this.props.message);

        if(prevProps.message !== this.props.message){
            console.log("NOT_EQUAL");
            this.setState(
                {
                    message : this.props.message, 
                    messageCopy: this.props.message.message || this.state.messageCopy2,
                    isSuccessCopy : this.props.message.isSuccess || this.state.isSuccessCopy2
                })
        }
    }
    
    render() {

        const {message } = this.state.message;
        const { messageCopy, isSuccessCopy2 } = this.state;

        return (
            <div id="message-container" className={message ? "show-message" : "hide-message"}>
                <div id="message" className={isSuccessCopy2 ? "success" : "failed"}>
                    <p>{messageCopy}</p>
                        <i className="fa fa-close" onClick={this.closeMessage}></i>
                </div>
            </div>
        )
    }
}

Message.propTypes = {
    message: PropTypes.object.isRequired,
    clearMessage: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    message: state.message
})

export default connect(mapStateToProps, {clearMessage})(Message);
