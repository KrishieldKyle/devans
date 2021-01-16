import React, { Component } from 'react'

import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getUser, addOrUpdateProfile } from "../../actions/userActions";
import { clearErrors } from '../../actions/errorActions';

// Import commons
import TextField from "../common/TextField";
import Spinner from "../common/Spinner";

// Import css
import "./EditProfile.css";

export class EditProfile extends Component {

    constructor(){
        super()
        this.state = {
            auth: {},
            errors: {},
            firstName: "",
            middleName: "",
            lastName: "",
            email: ""
        }

        this.onChange = this.onChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
    }

    componentDidUpdate(prevProps){
    
        if(!this.props.auth.isAuthenticated){
            this.props.history.push('/login')
        }
        if(prevProps.auth !== this.props.auth){
            this.setState({
                    auth : this.props.auth
                })
        }
        if(prevProps.errors !== this.props.errors){
            this.setState({
                    errors : this.props.errors
                })
        }

    }

    componentDidMount(){

        this.props.getUser(this.props.auth.user.userId, () => {
            this.setState({
                auth : this.props.auth,
                firstName: this.props.user.profile.firstName,
                lastName: this.props.user.profile.lastName,
                middleName: this.props.user.profile.middleName,
                email: this.props.user.profile.email
            })  
        });
        
        this.props.clearErrors();
    }

    onChange(e) {
        this.setState({ [e.target.name]: e.target.value })
    }

    onSubmit(e) {
        e.preventDefault();

        const profileData = {
            userId: this.state.auth.user.userId,
            firstName: this.state.firstName,
            middleName: this.state.middleName,
            lastName: this.state.lastName,
            email: this.state.email
        }
        console.log(profileData)
        this.props.addOrUpdateProfile(profileData);
    }

    render() {

        const { errors } = this.state;

        const { isSaveProfileLoading, isGetUserLoading } = this.props.user;

        let button;

        if(isSaveProfileLoading){
            button = <Spinner width={40} />
        }
        else {
            button = (
                <div className="buttonDiv">
                            <input type="submit" value="Add Profile" />
                        </div>
            )
        }

        let editProfileContent;

        if(isGetUserLoading){
            editProfileContent = <Spinner />
        }
        else {
            editProfileContent = (      <div className="profile-fields">
            <form onSubmit={this.onSubmit}>
                <TextField 
                    placeholder="First Name"
                    type="text"
                    name="firstName"
                    value={this.state.firstName}
                    error={errors.firstName}
                    onChange={this.onChange}
                />
                <TextField 
                    placeholder="Middle Name"
                    type="text"
                    name="middleName"
                    value={this.state.middleName}
                    error={errors.middleName}
                    onChange={this.onChange}
                />
                <TextField 
                    placeholder="Last Name"
                    type="text"
                    name="lastName"
                    value={this.state.lastName}
                    error={errors.lastName}
                    onChange={this.onChange}
                />
                <TextField 
                    placeholder="Email"
                    type="text"
                    name="email"
                    value={this.state.email}
                    error={errors.email}
                    onChange={this.onChange}
                />
                {button}
            </form>
        </div>)
        }


        return (
            <div id="edit-profile-container">
                <p id="edit-profile-page-title">Edit Profile</p>
                {editProfileContent}
            </div>
        )
    }
}

EditProfile.propTypes = {
    clearErrors: PropTypes.func.isRequired,
    addOrUpdateProfile: PropTypes.func.isRequired,
    getUser: PropTypes.func.isRequired,
    errors: PropTypes.object.isRequired,
    auth: PropTypes.object.isRequired,
    user: PropTypes.object.isRequired
}
const mapStateToProps = (state) => ({
    errors: state.errors,
    auth: state.auth,
    user: state.user
})
export default connect(mapStateToProps, {clearErrors, addOrUpdateProfile, getUser})(EditProfile)
