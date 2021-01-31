import React, { Component } from 'react'

import "./Technology.css";

export class Technology extends Component {
    render() {
        return (
            <div>
                {this.props.pendingRemove ? 
                <div className={this.props.isTechnologyEditClicked ? "swing technology-container pending-remove" : "technology-container"}>
                    <p>{this.props.technology}</p>
                    {this.props.isTechnologyEditClicked ? <i onClick={this.props.technologyRecycle} className="fa fa-recycle pending-remove"></i> : ""}
                </div>
                :
                <div className={this.props.isTechnologyEditClicked ? "swing technology-container" : "technology-container"}>
                    <p>{this.props.technology}</p>
                    {this.props.isTechnologyEditClicked ? <i onClick={this.props.technologyRemove} className="fa fa-trash"></i> : ""}
                </div>
                }
            </div>
    
        )
    }
}

export default Technology