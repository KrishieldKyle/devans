import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

// Import common
import Spinner from "../common/Spinner";
import Title from "../common/Title";
import Technology from "../common/Technology";

import { getUser, addOrUpdateProfile } from "../../actions/userActions";

// Import css
import "./Account.css"

export class Account extends Component {

    constructor(){
        super()
        this.state = {
            auth: {},
            user: {
                profile: {},
                titles: [],
                technologies: []
            }
        }

    }

    componentDidUpdate(prevProps){

        if(prevProps.auth !== this.props.auth){
            this.setState({
                auth: this.props.auth
            })
        }

        console.log("componentDidUpdate")
        
        if(prevProps.user !== this.props.user){
            this.setState({
                user: this.props.user
            })
        }
    }

    componentDidMount(){
        console.log("componentDidMount")
        this.props.getUser(this.props.auth.user.userId);
    }

    render() {

        const { user } = this.state;

        let titles = <Spinner width={20}/>;

        if(user.titles.length === 0){
            titles = <span>No titles found, <Link>add titles</Link>.</span>
        }
        else {
            titles = user.titles.map(title => {
            
                return (
                    <Title key={title.titleId} title = {title.name}/>
                )
            })
        }

        let technologies = <Spinner width={20}/>;

        if(user.technologies.length === 0){
            technologies = <span>No technologies found, <Link>add technologies</Link>.</span>
        }
        else {
            technologies = user.technologies.map(technology => {
            
                return (
                    <Technology key={technology.technologyId} technology = {technology.name}/>
                )
            })
        }

        let profileContent = (<div id="account-profile">
                                <span>No profile found, <Link>setup your profile</Link>.</span>
                            </div>)

        if(user.profile){
            profileContent = (<div id="account-profile">
            <div className="account-profile-information" id="profile-label">
                <p>Fullname: </p>
                <p>Email Address: </p>
            </div>
            <div className="account-profile-information" id="profile-info">
                <p>{`${user.profile.lastName}, ${user.profile.firstName} ${user.profile.middleName}`}</p>
                <p>{user.profile.email}</p>
            </div>
        </div>)
        }

        return (
            <div id="account-container">
                <p id="account-page-title">My Account</p>
                <div id="account-username-container" className="account-information">
                    <p className="account-label">Username</p>
                    <p id="account-username">{user.username}</p>
                </div>
                <div id="account-profile-container" className="account-information">
                    <p className="account-label">Profile</p>
                    {profileContent}
                </div>
                <div id="account-titles-container" className="account-information">
                    <p className="account-label">Titles</p>
                    {titles}
                </div>
                <div id="account-technologies-container" className="account-information">
                    <p className="account-label">Technologies</p>
                    {technologies}
                </div>
            </div>
        )
    }
}

Account.propTypes = {
    auth: PropTypes.object.isRequired,
    user: PropTypes.object.isRequired,
    getUser: PropTypes.func.isRequired,
    addOrUpdateProfile: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    user: state.user
})

export default connect(mapStateToProps, {getUser, addOrUpdateProfile})(Account)
