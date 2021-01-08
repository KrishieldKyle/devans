import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { loginUser } from '../../actions/authActions';
import { clearErrors } from '../../actions/errorActions';

// Import Commons
import Spinner from "../common/Spinner";
import TextField from "../common/TextField";

// Import Css
import "../../assets/css/Auth.css"

export class Login extends Component {

    constructor() {
        super()
        this.state = {
            username: '',
            password: '',
            errors: {},
            auth: {}
        }

        this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidUpdate(prevProps){

        if(this.props.auth.isAuthenticated){
            this.props.history.push('/dashboard')
        }
        if(prevProps.errors !== this.props.errors){
            this.setState({
                    errors : this.props.errors
                })
        }
        if(prevProps.auth !== this.props.auth){
            this.setState({
                    auth : this.props.auth
                })
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
            password: this.state.password
        }
        this.props.loginUser(userData);
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
                            <input type="submit" value="Login" />
                            <span>Don't have an account? <Link to="/register">Register</Link></span>
                        </div>
            )
        }

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
                        {button}
                        
                    </form>
                </div>
            </div>
        )
    }
}

Login.propTypes = {
    loginUser: PropTypes.func.isRequired,
    clearErrors: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    errors: state.errors
})

export default connect(mapStateToProps, { loginUser, clearErrors })(Login);
