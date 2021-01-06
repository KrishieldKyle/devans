import React, { Component } from 'react'
import PropTypes from 'prop-types';
import "../../assets/css/common/Message.css";
import { connect } from 'react-redux';

import { clearErrors } from "../../actions/errorActions";

export class Message extends Component {

    constructor() {
        super()
        this.state = {
            errors: {},
            messageCopy: "",
            messageCopy2: ""
        }
        
        this.closeMessage = this.closeMessage.bind(this);
    }

    closeMessage(){
        this.setState({messageCopy2: this.props.errors.message}, () => {
            this.props.clearErrors();
        })
    }

    // static getDerivedStateFromProps(nextProps, prevState){
    //     if (nextProps.errors !== prevState.errors) {
    //         console.log("getDerivedStateFromProps")
    //         return { errors: nextProps.errors, messageCopy: nextProps.errors.message};
    //     }
    //     else return null;
    // }

    componentDidUpdate(prevProps){

        if(prevProps.errors !== this.props.errors){
            this.setState({errors : this.props.errors, messageCopy: this.props.errors.message || this.state.messageCopy2})
        }
    }
    
    render() {

        const {success, message } = this.state.errors;
        const { messageCopy } = this.state;

        return (
            <div id="message-container" className={message ? "show-message" : "hide-message"}>
                <div id="message" className={success ? "success" : "failed"}>
                    <p>{messageCopy}</p>
                        <i className="fa fa-close" onClick={this.closeMessage}></i>
                </div>
            </div>
        )
    }
}

Message.propTypes = {
    errors: PropTypes.object.isRequired,
    clearErrors: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    errors: state.errors
})

export default connect(mapStateToProps, {clearErrors})(Message);
