import React, { Component } from 'react'

// Import default image
import defaultImage from "../../assets/images/defaultAvatar.png";

// Import common component
import Title from "../common/Title";

import Spinner from "../common/Spinner"

// Import css
import "./DeveloperCard.css";

export class DeveloperCard extends Component {

    render() {

        const { developer } = this.props;

        let titles = <Spinner width={20}/>;

        if(developer.titles.length === 0){
            titles = <p className="no-titles">Developer has no titles.</p>
        }
        else {
            titles = developer.titles.map(title => {
            
                return (
                    <Title key={title.titleId} title = {title.name}/>
                )
            })
        }
        

        return (
            <div className="developer-card-container">
                <div className="developer-card-image">
                    <img
                        src={defaultImage}
                        style={{ width: '100px', margin: 'auto', display: 'block' }}
                        alt="avatar"
                    />
                </div>
                <div className="developer-card-info">
                    <p className="developer-card-fullname">{ developer.profile ? `${developer.profile.lastName}, ${developer.profile.firstName} ${developer.profile.middleName || ""}` : developer.username}</p>
                    <div className="developer-card-titles">
                        {titles}
                    </div>
                </div>
            </div>
        )
    }
}


export default DeveloperCard
