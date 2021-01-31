import React, { Component } from 'react'
import PropTypes from 'prop-types';
import "./Message.css";
import { connect } from 'react-redux';

import { closeMessage } from "../../actions/messageActions";

export class Message extends Component {

    constructor() {
        super()
        this.state = {
            message: {},
            messageTimer: null,
            messageInterval: null,
            loadingPercentage: 10,
            isSuccessCopy: false,
            messageCopy: ""
        }
        
        this.closeMessage = this.closeMessage.bind(this);
    }

    closeMessage(){
        clearTimeout(this.state.messageTimer);
        clearInterval(this.state.messageInterval)
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
                    clearInterval(this.state.messageInterval)
                    this.setState({
                        messageInterval : setInterval(() => {
                            this.setState({
                                loadingPercentage: this.state.loadingPercentage+10
                            }, () => {
                                // console.log(parseFloat((this.state.loadingPercentage / 5000) * 100).toFixed(2).toString().replace(/\.00$/, ''));
                            })
                            
                        }, 10),
                        messageTimer : setTimeout(() => {
                            this.props.closeMessage();
                            clearInterval(this.state.messageInterval)
                        }, 5500)
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
                    <div id="message-loading-bar">
                        <div />
                    </div>
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
