import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

import ReactMoment from "react-moment";

// Import common
import Spinner from "../common/Spinner";
import Title from "../common/Title";
import Technology from "../common/Technology";
import TextField from "../common/TextField";
import Button from "../common/Button";

import { getUser, addOrUpdateProfile, updateUserTitles } from "../../actions/userActions";
import { getTitles } from "../../actions/titlesActions";

// Import css
import "./Account.css"

export class Account extends Component {

    constructor(){
        super()
        this.state = {
            auth: {},
            titles: {},
            titleCollasibleDiv: {
                minHeight: "0px",
                padding: "0px 0px 0px 0px",
            },
            titleCollasibleHeaderDiv: {
                minHeight: "0px",
                padding: "0px 0px 0px 0px",
            },
            isTitleEditActive: false,
            technologyDivHeight: 0,
            inputedTitle: {
                title: "",
                error: ""
            },
            tempTitlesList: {
                titles:[],
                removeTitles: [],
                isLoading: false
            },
            pendingRemovedTitles: [],
            user: {
                profile: {},
                titles: [],
                technologies: []
            }
        }
       
        this.onTitleEditClicked = this.onTitleEditClicked.bind(this);
        this.onChange = this.onChange.bind(this);
        this.addTitleOnTitleTempList = this.addTitleOnTitleTempList.bind(this);
    }

    componentDidUpdate(prevProps){

        if(prevProps.auth !== this.props.auth){
            this.setState({
                auth: this.props.auth
            })
        }
        if(prevProps.user !== this.props.user){
            this.setState({
                user: this.props.user
            })
        }

        if(prevProps.titles !== this.props.titles){
            this.setState({
                titles: this.props.titles
            })
        }
    }

    onTitleEditClicked(){

        this.setState({
            isTitleEditActive : !this.state.isTitleEditActive

        }, () => {

            if(this.state.isTitleEditActive){
                this.setState({
                    titleCollasibleDiv : {
                        minHeight: "200px",
                        padding: "15px 0px 0px 0px",
                    },
                    titleCollasibleHeaderDiv: {
                        minHeight: "50px",
                        padding: "0px 0px 0px 0px",
                    }
                })
            }
            else{

                const {tempTitlesList, user} = this.state;

                let copyUserTitles = user.titles;
                let copyTempTitles = tempTitlesList.titles;
                let removedTitles = tempTitlesList.removeTitles;

                if(copyTempTitles.length !== 0 || removedTitles.length !== 0){
                    let newTitles = [...copyUserTitles, ...copyTempTitles]

                    newTitles = newTitles.filter((newTitle) => {
                        return !removedTitles.find((removeTitle) => {
                          return newTitle.titleId === removeTitle.titleId
                        })
                      })
                    //   console.log(newTitles)

                      const titlesData = {
                          userId: this.state.user.userId,
                          titles: newTitles
                      }

                    this.props.updateUserTitles(titlesData)
                }

                this.setState({
                    titleCollasibleDiv: {
                        minHeight: "0px",
                        padding: "0px 0px 0px 0px",
                    },
                    titleCollasibleHeaderDiv: {
                        minHeight: "0px",
                        padding: "0px 0px 0px 0px",
                    },
                    inputedTitle: {
                        title: "",
                        error: ""
                    },
                    tempTitlesList: {
                        titles:[],
                        removeTitles: [],
                        isLoading: false
                    },
                    pendingRemovedTitles: []
                })
            }
        }) 
    }

    addTitleOnTitleTempList(){

        const {inputedTitle, titles, tempTitlesList, user}= this.state;

        if(inputedTitle.title === ""){
            this.setState({
                inputedTitle: {
                    ...inputedTitle,
                    error: "Please enter a title"
                }
            })
            return;
        }

        this.setState({
            tempTitlesList: {
                ...tempTitlesList,
                isLoading: true
            }
        }, () => {
            let isTitleAlreadyAdded = false;

            for(let x=0; x<tempTitlesList.titles.length; x++){
                if(tempTitlesList.titles[x].name.toLowerCase() === inputedTitle.title.toLowerCase()){
                    isTitleAlreadyAdded= true;
                }
                break;
            }

            for(let x=0; x<user.titles.length; x++){
                if(user.titles[x].name.toLowerCase() === inputedTitle.title.toLowerCase()){
                    isTitleAlreadyAdded= true;
                }
                break;
            }

            if(!isTitleAlreadyAdded){
                let newTitle = {
                    titleId: 0,
                    name: inputedTitle.title
                };

                for(let x = 0; x<titles.titles.length; x++){
                    if(titles.titles[x].name.toLowerCase() === inputedTitle.title.toLowerCase()){
        
                        newTitle = {
                            titleId: titles.titles[x].titleId,
                            name : titles.titles[x].name
                        }
                        break;  
                    }
                }

                this.setState({
                    tempTitlesList: {
                        ...tempTitlesList,
                        titles: [...tempTitlesList.titles, newTitle]
                    },
                    inputedTitle: {
                        error: "",  
                        title: ""
                    }
                }, () => {
                    this.setState({
                        tempTitlesList: {
                            ...this.state.tempTitlesList,
                            isLoading: false
                        }
                    })
                    console.log(this.state.tempTitlesList)
                })
            }
            else {
                this.setState({
                    tempTitlesList: {
                        ...this.state.tempTitlesList,
                        isLoading: false
                    },
                    inputedTitle: {
                        ...inputedTitle,
                        error: "Please enter new title"
                    }
                })
            }
        })
    }

    onTitleRemoveFromUserClicked(removedTitle){

        const {tempTitlesList, pendingRemovedTitles} = this.state;

        if(pendingRemovedTitles.indexOf(removedTitle.titleId) == -1){

            const newRemovedTitlesList = tempTitlesList.removeTitles;
            newRemovedTitlesList.push(removedTitle);
            const newPendingRemoveTitles = pendingRemovedTitles;
            newPendingRemoveTitles.push(removedTitle.titleId)
    
            this.setState({
                tempTitlesList: {
                    ...tempTitlesList,
                    removeTitles: newRemovedTitlesList
                },
                pendingRemovedTitles: newPendingRemoveTitles
            },() => {
                console.log(this.state.tempTitlesList.removeTitles);
            })
        }
    }

    onTitleRecycleFromUserClicked(recycleTitle){

        const {pendingRemovedTitles, tempTitlesList} = this.state;

        const newPendingRemoveTitles = pendingRemovedTitles;
        const newRemoveTitles = tempTitlesList.removeTitles;

        let index = newPendingRemoveTitles.indexOf(recycleTitle.titleId)

        newPendingRemoveTitles.splice(index, 1)
        newRemoveTitles.splice(index, 1)

        this.setState({
            pendingRemovedTitles: newPendingRemoveTitles,
            tempTitlesList: {
                ...tempTitlesList,
                removedTitles: newRemoveTitles
            }
        })

    }

    onTitleRemoveFromPendingClicked(title){
        
        const {tempTitlesList} = this.state;

        let newTempTitles = tempTitlesList.titles;

        for(let x=0; x<tempTitlesList.titles.length; x++){
            if(tempTitlesList.titles[x].name.toLowerCase() === title.toLowerCase() ){
                newTempTitles.splice(x, 1);
                break;
            }
        }
        this.setState({
            tempTitlesList: {
                ...tempTitlesList,
                titles: newTempTitles
            }
        })
    }
    
    onChange(e) {
        let target = e.target.name;

        const splitedTarget = target.split(".");

        if(splitedTarget.length >1){

            const origObject = eval(`this.state.${splitedTarget[0]}`)

            let slicedSplitedTarget = splitedTarget.slice(1, splitedTarget.length)

            let jsonString = "{";
            slicedSplitedTarget.forEach((target, index) => {
                jsonString = jsonString + `"${target}" : `
                if(index===slicedSplitedTarget.length-1){
                    
                    jsonString = jsonString + `"${e.target.value}"`
                }
                else{
                    jsonString = jsonString + `{`
                }
            })

            for(let x=0; x<slicedSplitedTarget.length; x++){
                jsonString = jsonString + `}`
            }
            const jsonObject = JSON.parse(jsonString);

            const newObject = {...origObject, ...jsonObject}

            this.setState({[splitedTarget[0]] : newObject})
        }
        else {
            this.setState({[e.target.name] : e.target.value})
        }
        
    }

    componentDidMount(){
        this.props.getUser(this.props.auth.user.userId, ()=>{});
        this.props.getTitles();
    }

    render() {

        const { user, isTitleEditActive, titleCollasibleDiv, titleCollasibleHeaderDiv,inputedTitle, tempTitlesList, pendingRemovedTitles } = this.state;

        let pendingTitles;

        let titleAddButton;

        if(tempTitlesList.isLoading){
            pendingTitles = <Spinner width={20}/>;
            titleAddButton = <Spinner width={20}/>;
        }
        else{
            titleAddButton = <Button value="Add Title" fontWeight="500" onClick={this.addTitleOnTitleTempList}/>

            if(tempTitlesList.titles.length === 0){
                pendingTitles = <span>Nothing to show</span>;
            }
            else {
                pendingTitles = tempTitlesList.titles.map(title => {
                    return (
                            <Title 
                            titleRemove={() => this.onTitleRemoveFromPendingClicked(title.name)} 
                            isTitleEditClicked = {isTitleEditActive} 
                            key = {title.name} 
                            title = {title.name}/>
                    )
                })
            }
            
        }

        let titles;

        if(user.isUpdateUserTitlesLoading){
            titles = <Spinner width={20}/>;
        }
        else {
            if(user.titles.length === 0){
                if(isTitleEditActive){
                    titles = <Button value="Save Titles" margin="5px 0px 5px 0px" fontWeight="500" onClick={this.onTitleEditClicked}/>
                }
                else{
                    titles = <span>No titles found, <Link to="#" onClick={this.onTitleEditClicked}>add titles</Link></span>
                }
            }
            else {
                titles = user.titles.map(title => {
    
                    let pendingRemove = false;
    
                    if(pendingRemovedTitles.indexOf(title.titleId) !== -1){
                        pendingRemove= true;
                    }
    
                    return (<Title 
                        titleRemove={() => this.onTitleRemoveFromUserClicked({
                                titleId: title.titleId, 
                                name: title.name
                            })} 
                        titleRecycle={() => this.onTitleRecycleFromUserClicked({
                            titleId: title.titleId, 
                            name: title.name
                        })}
                        pendingRemove={pendingRemove}
                        isTitleEditClicked = {isTitleEditActive} 
                        key = {title.titleId} 
                        title = {title.name}/>)
                    
                })
            }
        }
        

        let technologies = <Spinner width={20}/>;

        if(user.technologies.length === 0){
            technologies = <span>No technologies found, <Link >add technologies</Link>.</span>
        }
        else {
            technologies = user.technologies.map(technology => {
            
                return (
                    <Technology key={technology.technologyId} technology = {technology.name}/>
                )
            })
        }

        let profileContent = (<div id="account-profile">
                                <span>No profile found, <Link to="/edit-profile">setup your profile</Link>.</span>
                            </div>)

        if(user.profile.userId !== 0){
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
                <div className="account-information">
                    <p className="account-label">Titles</p>
                    {user.titles.length === 0 ? "" : 
                        <i onClick={this.onTitleEditClicked} 
                        className={isTitleEditActive ? "fa fa-save account-edit": "fa fa-pencil account-edit"} 
                        title={isTitleEditActive ? "Save": "Edit"} 
                        />}
                    <div id="title-collapsible-header-div" style={titleCollasibleHeaderDiv}>
                        <p>Add or Remove a Title</p>
                    </div>
                    <div id="titles-div">
                        {titles}
                    </div>
                    <div id="title-collapsible-div" style={titleCollasibleDiv}>
                        <div className="title-input-div">
                            <TextField 
                                name="inputedTitle.title"
                                placeholder="Input Title"
                                value={inputedTitle.title}
                                onChange={this.onChange}
                                width="60%"
                                margin="0px"
                                error= {inputedTitle.error}
                            />
                            {titleAddButton}
                        </div>
                        <div id="pending-titles-div">
                            <p className="pending-titles-header">Titles to be added:</p>
                            <div className="pending-titles">
                                {pendingTitles}
                            </div>
                        </div>
                    </div>
                </div>
                <div ref={this.technologyDiv} className="account-information">
                    <p className="account-label">Technologies</p>
                    {user.technologies.length === 0 ? "" : <Link to="edit-profile"><i className="fa fa-pencil" /></Link>}
                    {technologies}
                </div>
            </div>
        )
    }
}

Account.propTypes = {
    auth: PropTypes.object.isRequired,
    user: PropTypes.object.isRequired,
    titles: PropTypes.object.isRequired,
    getUser: PropTypes.func.isRequired,
    addOrUpdateProfile: PropTypes.func.isRequired,
    updateUserTitles: PropTypes.func.isRequired,
    getTitles: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    user: state.user,
    titles: state.titles
})

export default connect(mapStateToProps, {getUser, addOrUpdateProfile, getTitles, updateUserTitles})(Account)
