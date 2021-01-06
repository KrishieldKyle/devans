import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Provider } from 'react-redux';

import store from './store';
import PrivateRoute from './components/common/PrivateRoute';

// Components
import Login from "./components/auth/Login";
import Developers from "./components/developers/Developers";

// Import Css
import './App.css';

import Navbar from './components/layout/Navbar';

class App extends Component {

  render (){


    return (
      <Provider store={store}>
        <Router>
          <div id="app">
            <Navbar />
            <div id="main-container">
              <Route exact path="/login" component={Login} />
              <Route exact path="/developers" component={Developers} />
            </div>
          </div>
        </Router>
      </Provider>
      
    );
  }
}


export default App;
