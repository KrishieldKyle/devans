import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { getDevelopers } from '../../actions/developersActions';

// Import components
import DeveloperCard from "./DeveloperCard";
import Spinner from "../common/Spinner"

// import css
import "./Developers.css";

export class Developers extends Component {

    constructor() {
        super()
        this.state = {
            developers: [],
        }
        
    }

    static getDerivedStateFromProps(nextProps, prevState){
        if (nextProps.developers !== prevState.developers) {
            return { developers: nextProps.developers };
        }
        else return null;
    }

    // componentDidUpdate(prevProps){

    //     if(prevProps.developers !== this.props.developers){
    //         this.setState({developers : this.props.developers})
    //     }
    // }

    componentDidMount(){
        console.log("componentDidMount")
        this.props.getDevelopers();
    }

    render() {

        const { developers, isLoading } = this.state.developers;

        let developersList;

        if (developers === null || isLoading) {
            developersList = <Spinner />;
        } else {
            developersList = developers.map(developer => (
                <DeveloperCard key={developer.userId} developer={developer} />
            ))
        }

        return (
            <div id="developers-container">
                <p>List of Developers</p>
                <div id="developer-cards">
                    {developersList}
                </div>
            </div>
        )
    }
}

Developers.propTypes = {
    getDevelopers: PropTypes.func.isRequired,
    developers: PropTypes.object.isRequired
}

const mapStateToProps = (state) => ({
    developers: state.developers
})

export default connect(mapStateToProps, { getDevelopers })(Developers);
