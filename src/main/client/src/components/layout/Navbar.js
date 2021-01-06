import React, { Component } from 'react'
import { Link } from 'react-router-dom';

// Css
import '../../assets/css/Navbar.css';

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
                        <ul>
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
                        </ul>
                    </div>
                    <Message />
            </div>
        )
    }
}

export default Navbar
