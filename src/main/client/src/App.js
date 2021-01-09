import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import jwt_decode from 'jwt-decode';
import setAuthToken from './utils/setAuthToken';
import { setCurrentUser, logoutUser } from './actions/authActions';
import { Provider } from 'react-redux';
import moment from "moment-timezone";

import store from './store';
import PrivateRoute from './components/common/PrivateRoute';

// Components
import Login from "./components/auth/Login";
import Home from "./components/home/Home";
import Register from "./components/auth/Register";
import Developers from "./components/developers/Developers";

// Import Css
import './App.css';

import Navbar from './components/layout/Navbar';

// Check for token
if (localStorage.jwtToken) {
  // Set auth token header auth
  setAuthToken(localStorage.jwtToken);
  // Decode token and get user info and exp
  const decoded = jwt_decode(localStorage.jwtToken);
  // Set user and isAuthenticated
  store.dispatch(setCurrentUser(decoded));

  // Check for expired token
  const currentTime = Date.now() / 1000;
  if (decoded.exp < currentTime) {
    // Logout user
    store.dispatch(logoutUser());
    // Clear current Profile
    // store.dispatch(clearCurrentProfile());
    // Redirect to login
    window.location.href = '/login';
  }
}

class App extends Component {

  render (){


    return (
      <Provider store={store}>
        <Router>
          <div id="app">
            <Navbar />
            <div id="main-container">
              <Route exact path="/" component={Home} />
              <Route exact path="/login" component={Login} />
              <Route exact path="/register" component={Register} />
              <Route exact path="/developers" component={Developers} />
            </div>
          </div>
        </Router>
      </Provider>
      
    );
  }
}


export default App;
