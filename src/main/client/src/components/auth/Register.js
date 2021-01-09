import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { registerUser } from '../../actions/authActions';
import { clearErrors } from '../../actions/errorActions';
import { closeMessage } from '../../actions/messageActions';

// Import Commons
import TextField from "../common/TextField";
import Spinner from "../common/Spinner";

// Import Css
import "./Auth.css"

export class Register extends Component {

    constructor() {
        super()
        this.state = {
            username: '',
            password: '',
            confirmPassword: '',
            auth: {},
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidUpdate(prevProps){
        if(this.props.auth.isAuthenticated){
            this.props.history.push('/dashboard')
        }
      
        if(prevProps.errors !== this.props.errors){
            this.setState({errors : this.props.errors})
        }

        if(prevProps.auth !== this.props.auth){
            this.setState({auth : this.props.auth})
        }
    }

    componentDidMount(){
        this.props.clearErrors();
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();

        const userData = {
            username: this.state.username,
            password: this.state.password,
            confirmPassword: this.state.confirmPassword
        }
        this.props.closeMessage();
        this.props.registerUser(userData);
    }

    render() {
        const { errors } = this.state;
        const { isLoading } = this.state.auth;

        let button;

        if(isLoading){
            button = <Spinner width={40} />
        }
        else {
            button = (
                <div className="buttonDiv">
                            <input type="submit" value="Register" />
                            <span>Already have an account? <Link to="/login">Login</Link></span>
                        </div>
            )
        }


        return (
            <div className="auth-main-div">
                <p>Register to Devans</p>
                <div className="auth-fields">
                    <form onSubmit={this.onSubmit}>
                        <TextField 
                            placeholder="Username"
                            type="text"
                            name="username"
                            value={this.state.username}
                            error={errors.message ? " " : errors.username}
                            onChange={this.onChange}
                        />
                        <TextField 
                            placeholder="Password"
                            type="password"
                            name="password"
                            value={this.state.password}
                            error={errors.password}
                            onChange={this.onChange}
                        />
                        <TextField 
                            placeholder="Confirm Password"
                            type="password"
                            name="confirmPassword"
                            value={this.state.confirmPassword}
                            error={errors.confirmPassword}
                            onChange={this.onChange}
                        />
                        {button}
                        
                    </form>
                </div>
            </div>
        )
    }
}

Register.propTypes = {
    registerUser: PropTypes.func.isRequired,
    closeMessage: PropTypes.func.isRequired,
    clearErrors: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    errors: state.errors
})

export default connect(mapStateToProps, { registerUser, clearErrors, closeMessage })(Register);
