import React, { Component } from 'react'
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';

import { logoutUser } from "../../actions/authActions";

// Css
import './Navbar.css';

import Message from "../common/Message";

export class Navbar extends Component {

    constructor(props) {
        super(props);
        this.state = {
          isHamburgerOpen: false
        };

        this.onHamburgerCliked = this.onHamburgerCliked.bind(this);
    }

    onHamburgerCliked(e){ 
        this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})
    }

    render() {

        const { isAuthenticated } = this.props.auth;

        let navOptions;

        if(isAuthenticated){
            navOptions = (<ul>
                <li>
                    <Link to="/account" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Account</Link>
                </li>
                <li>
                    <Link to="/notifications" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Notifications</Link>
                </li>
                <li>
                    <Link to="/logout" onClick={() => {
                        this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})
                        this.props.logoutUser();
                    }}>Logout</Link>
                </li>
                <div className="line-div-right" />
                <li>
                    <Link to="/discussions" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Discussions</Link>
                </li>
                <li>
                    <Link to="/developers" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Developers</Link>
                </li>
            </ul>);
    
        }
        else {
            navOptions = (<ul>
                <li>
                    <Link to="/login" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Login</Link>
                </li>
                <li>
                    <Link to="/register" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Register</Link>
                </li>
                <div className="line-div-right" />
                <li>
                    <Link to="/discussions" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Discussions</Link>
                </li>
                <li>
                    <Link to="/developers" onClick={() => this.setState({isHamburgerOpen : !this.state.isHamburgerOpen})}>Developers</Link>
                </li>
            </ul>);
        }
        return (
            <div id="nav-main">
                    <div id="nav-container" >
                        <div className="nav-logo">
                            <span id="home" >Devans</span>
                        </div>
                        <div className="spacer" />
                        <div id="hamburger-menu-mobile" className={this.state.isHamburgerOpen ? "hamburger-open" : ""} onClick={this.onHamburgerCliked}>
                            <div className="hamburger-div" />
                        </div>
                    </div>
                    <div id="collapsible-navbar-mobile" className={this.state.isHamburgerOpen ? "show-collapsible-div" : "hide-collapsible-div"}>
                        <div className="spacer" />
                        {navOptions}
                    </div>
                    <Message />
            </div>
        )
    }
}

Navbar.propTypes = {
    auth: PropTypes.object.isRequired,
    logoutUser: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth
})

export default connect(mapStateToProps, {logoutUser})(Navbar)
