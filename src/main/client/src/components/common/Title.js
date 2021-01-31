import React, { Component } from 'react'

import "./Title.css";

export class Title extends Component {
    render() {
        return (
            <div>
                {this.props.pendingRemove ? 
                <div className={this.props.isTitleEditClicked ? "swing title-container pending-remove" : "title-container"}>
                    <p>{this.props.title}</p>
                    {this.props.isTitleEditClicked ? <i onClick={this.props.titleRecycle} className="fa fa-recycle pending-remove"></i> : ""}
                </div>
                :
                <div className={this.props.isTitleEditClicked ? "swing title-container" : "title-container"}>
                    <p>{this.props.title}</p>
                    {this.props.isTitleEditClicked ? <i onClick={this.props.titleRemove} className="fa fa-trash"></i> : ""}
                </div>
            
                }
            </div>
        )
    }
}

export default Title