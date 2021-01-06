import React, { Component } from 'react'
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { loginUser } from '../../actions/authActions';

// Import Commons
import TextField from "../common/TextField";

// Import Css
import "../../assets/css/Auth.css"

export class Login extends Component {

    constructor() {
        super()
        this.state = {
            username: '',
            password: '',
            errors: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.auth.isAuthenticated) {
            this.props.history.push('/dashboard')
        }
        if (nextProps.errors) {
            this.setState({ errors: nextProps.errors });
        }
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();

        const userData = {
            username: this.state.username,
            password: this.state.password
        }
        this.props.loginUser(userData);
    }

    render() {
        const { errors } = this.state;

        return (
            <div className="auth-main-div">
                <p>Login to Devans</p>
                <div className="auth-fields">
                    <form onSubmit={this.onSubmit}>
                        <TextField 
                            placeholder="Username"
                            type="text"
                            name="username"
                            value={this.state.username}
                            error={errors.username}
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
                        <div className="buttonDiv">
                            <input type="submit" value="Login" />
                            <span>Don't have an account? <a to="/register">Register</a></span>
                        </div>
                        
                    </form>
                </div>
            </div>
        )
    }
}

Login.propTypes = {
    loginUser: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    errors: state.errors
})

export default connect(mapStateToProps, { loginUser })(Login);
