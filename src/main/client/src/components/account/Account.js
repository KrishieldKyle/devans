import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

import moment from "moment-timezone";
import ReactMoment from "react-moment";

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
        this.props.getUser(this.props.auth.user.userId, ()=>{});
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
                    <div key={title.titleId}>
                        <Link to="edit-profile"><i className="fa fa-pencil" /></Link>
                        <Title key={title.titleId} title = {title.name}/>
                    </div>
                )
            })
        }

        let technologies = <Spinner width={20}/>;

        if(user.technologies.length === 0){
            technologies = <span>No technologies found, <Link >add technologies</Link>.</span>
        }
        else {
            technologies = user.technologies.map(technology => {
            
                return (
                    <div key={technology.technologyId}>
                        <Link to="/edit-profile"><i className="fa fa-pencil" /></Link>
                        <Technology key={technology.technologyId} technology = {technology.name}/>
                    </div>
                )
            })
        }

        let profileContent = (<div id="account-profile">
                                <span>No profile found, <Link to="/edit-profile">setup your profile</Link>.</span>
                            </div>)

        if(user.profile){
            profileContent = (<div id="account-profile">
                <Link to="/edit-profile"><i className="fa fa-pencil" /></Link>
                <div id="account-profile-information">
                    <p><strong>Fullname: </strong>{`${user.profile.lastName}, ${user.profile.firstName} ${user.profile.middleName}`}</p>
                    <p><strong>Email Address: </strong>{user.profile.email}</p>
                    <p><strong>Created At: </strong>{user.profile.createdAt ? <ReactMoment tz="Asia/Manila" format="lll">{user.profile.createdAt}</ReactMoment> : ""} </p>
                    <p><strong>Last Update: </strong><ReactMoment tz="Asia/Manila" format="lll">{user.profile.updatedAt}</ReactMoment> </p>
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
