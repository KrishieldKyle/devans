import React, { Component } from 'react'
import { Link } from 'react-router-dom';

// Import css
import "./Home.css"

export class Home extends Component {
    render() {
        return (
            <div id="home-container">
                <p>Welcome to Devans</p>
                <div id="home-description-container">
                    <div id="home-description">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut 
                        labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco 
                        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in 
                        voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat 
                        non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </div>
                </div>
                <div id="home-options">
                    <Link to="/developers">Developers</Link>
                    <Link to="/discussions">Discussions</Link>
                </div>
            </div>
        )
    }
}

export default Home
