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

import { getUser, addOrUpdateProfile, updateUserTitles, updateUserTechnologies } from "../../actions/userActions";
import { getTitles } from "../../actions/titlesActions";
import { getTechnologies } from "../../actions/technologiesActions";

// Import css
import "./Account.css"

export class Account extends Component {

    constructor(){
        super()
        this.state = {
            auth: {},
            titles: {},
            technologies: {},
            titleCollasibleDiv: {
                height: "0px",
                padding: "0px 0px 0px 0px",
            },
            titleCollasibleHeaderDiv: {
                height: "0px",
                padding: "0px 0px 0px 0px",
            },
            technologyCollasibleDiv: {
                height: "0px",
                padding: "0px 0px 0px 0px",
            },
            technologyCollasibleHeaderDiv: {
                height: "0px",
                padding: "0px 0px 0px 0px",
            },
            isTitleEditActive: false,
            isTechnologyEditActive: false,
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
            inputedTechnology: {
                technology: "",
                error: ""
            },
            tempTechnologiesList: {
                technologies:[],
                removeTechnologies: [],
                isLoading: false
            },
            pendingRemovedTechnologies: [],
            user: {
                profile: {},
                titles: [],
                technologies: []
            }
        }
       
        this.onTitleEditClicked = this.onTitleEditClicked.bind(this);
        this.onTechnologyEditClicked = this.onTechnologyEditClicked.bind(this);
        this.onChange = this.onChange.bind(this);
        this.addTitleOnTitleTempList = this.addTitleOnTitleTempList.bind(this);
        this.addTechnologyOnTechnologyTempList = this.addTechnologyOnTechnologyTempList.bind(this);
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

        if(prevProps.technologies !== this.props.technologies){
            this.setState({
                technologies: this.props.technologies
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
                        height: "200px",
                        padding: "15px 0px 0px 0px",
                    },
                    titleCollasibleHeaderDiv: {
                        height: "50px",
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
                        height: "0px",
                        padding: "0px 0px 0px 0px",
                    },
                    titleCollasibleHeaderDiv: {
                        height: "0px",
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

    onTechnologyEditClicked(){

        this.setState({
            isTechnologyEditActive : !this.state.isTechnologyEditActive

        }, () => {

            if(this.state.isTechnologyEditActive){
                this.setState({
                    technologyCollasibleDiv : {
                        minHeight: "200px",
                        padding: "15px 0px 0px 0px",
                    },
                    technologyCollasibleHeaderDiv: {
                        minHeight: "50px",
                        padding: "0px 0px 0px 0px",
                    }
                })
            }
            else{

                const {tempTechnologiesList, user} = this.state;

                let copyUserTechnologies = user.technologies;
                let copyTempTechnologies = tempTechnologiesList.technologies;
                let removedTechnologies = tempTechnologiesList.removeTechnologies;

                if(copyTempTechnologies.length !== 0 || removedTechnologies.length !== 0){
                    let newTechnologies = [...copyUserTechnologies, ...copyTempTechnologies]

                    newTechnologies = newTechnologies.filter((newTechnology) => {
                        return !removedTechnologies.find((removeTechnology) => {
                          return newTechnology.technologyId === removeTechnology.technologyId
                        })
                      })
                    //   console.log(newTechnologies)

                      const technologiesData = {
                          userId: this.state.user.userId,
                          technologies: newTechnologies
                      }

                    this.props.updateUserTechnologies(technologiesData)
                }

                this.setState({
                    technologyCollasibleDiv: {
                        minHeight: "0px",
                        padding: "0px 0px 0px 0px",
                    },
                    technologyCollasibleHeaderDiv: {
                        minHeight: "0px",
                        padding: "0px 0px 0px 0px",
                    },
                    inputedTechnology: {
                        technology: "",
                        error: ""
                    },
                    tempTechnologiesList: {
                        technologies:[],
                        removeTechnologies: [],
                        isLoading: false
                    },
                    pendingRemovedTechnologies: []
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

    addTechnologyOnTechnologyTempList(){

        const {inputedTechnology, technologies, tempTechnologiesList, user}= this.state;

        if(inputedTechnology.technology === ""){
            this.setState({
                inputedTechnology: {
                    ...inputedTechnology,
                    error: "Please enter a technology"
                }
            })
            return;
        }

        this.setState({
            tempTechnologiesList: {
                ...tempTechnologiesList,
                isLoading: true
            }
        }, () => {
            let isTechnologyAlreadyAdded = false;

            for(let x=0; x<tempTechnologiesList.technologies.length; x++){
                if(tempTechnologiesList.technologies[x].name.toLowerCase() === inputedTechnology.technology.toLowerCase()){
                    isTechnologyAlreadyAdded= true;
                }
                break;
            }

            for(let x=0; x<user.technologies.length; x++){
                if(user.technologies[x].name.toLowerCase() === inputedTechnology.technology.toLowerCase()){
                    isTechnologyAlreadyAdded= true;
                }
                break;
            }

            if(!isTechnologyAlreadyAdded){
                let newTechnology = {
                    technologyId: 0,
                    name: inputedTechnology.technology
                };
                
                for(let x = 0; x<technologies.technologies.length; x++){
                    if(technologies.technologies[x].name.toLowerCase() === inputedTechnology.technology.toLowerCase()){
        
                        newTechnology = {
                            technologyId: technologies.technologies[x].technologyId,
                            name : technologies.technologies[x].name
                        }
                        break;  
                    }
                }

                this.setState({
                    tempTechnologiesList: {
                        ...tempTechnologiesList,
                        technologies: [...tempTechnologiesList.technologies, newTechnology]
                    },
                    inputedTechnology: {
                        error: "",  
                        technology: ""
                    }
                }, () => {
                    this.setState({
                        tempTechnologiesList: {
                            ...this.state.tempTechnologiesList,
                            isLoading: false
                        }
                    })
                    console.log(this.state.tempTechnologiesList)
                })
            }
            else {
                this.setState({
                    tempTechnologiesList: {
                        ...this.state.tempTechnologiesList,
                        isLoading: false
                    },
                    inputedTechnology: {
                        ...inputedTechnology,
                        error: "Please enter new technology"
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

    onTechnologyRemoveFromUserClicked(removedTechnology){

        const {tempTechnologiesList, pendingRemovedTechnologies} = this.state;

        if(pendingRemovedTechnologies.indexOf(removedTechnology.technologyId) == -1){

            const newRemovedTechnologiesList = tempTechnologiesList.removeTechnologies;
            newRemovedTechnologiesList.push(removedTechnology);
            const newPendingRemoveTechnologies = pendingRemovedTechnologies;
            newPendingRemoveTechnologies.push(removedTechnology.technologyId)
    
            this.setState({
                tempTechnologiesList: {
                    ...tempTechnologiesList,
                    removeTechnologies: newRemovedTechnologiesList
                },
                pendingRemovedTechnologies: newPendingRemoveTechnologies
            },() => {
                console.log(this.state.tempTechnologiesList.removeTechnologies);
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

    onTechnologyRecycleFromUserClicked(recycleTechnology){

        const {pendingRemovedTechnologies, tempTechnologiesList} = this.state;

        const newPendingRemoveTechnologies = pendingRemovedTechnologies;
        const newRemoveTechnologies = tempTechnologiesList.removeTechnologies;

        let index = newPendingRemoveTechnologies.indexOf(recycleTechnology.technologyId)

        newPendingRemoveTechnologies.splice(index, 1)
        newRemoveTechnologies.splice(index, 1)

        this.setState({
            pendingRemovedTechnologies: newPendingRemoveTechnologies,
            tempTechnologiesList: {
                ...tempTechnologiesList,
                removedTechnologies: newRemoveTechnologies
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

    onTechnologyRemoveFromPendingClicked(technology){
        
        const {tempTechnologiesList} = this.state;

        let newTempTechnologies = tempTechnologiesList.technologies;

        for(let x=0; x<tempTechnologiesList.technologies.length; x++){
            if(tempTechnologiesList.technologies[x].name.toLowerCase() === technology.toLowerCase() ){
                newTempTechnologies.splice(x, 1);
                break;
            }
        }
        this.setState({
            tempTechnologiesList: {
                ...tempTechnologiesList,
                technologies: newTempTechnologies
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
        this.props.getTechnologies();
    }

    render() {

        const { user, isTitleEditActive, isTechnologyEditActive, titleCollasibleDiv, technologyCollasibleDiv, titleCollasibleHeaderDiv, technologyCollasibleHeaderDiv,inputedTitle,inputedTechnology, tempTitlesList, tempTechnologiesList, pendingRemovedTitles, pendingRemovedTechnologies } = this.state;

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

        let pendingTechnologies;

        let technologyAddButton;

        if(tempTechnologiesList.isLoading){
            pendingTechnologies = <Spinner width={20}/>;
            technologyAddButton = <Spinner width={20}/>;
        }
        else{
            technologyAddButton = <Button value="Add Technology" fontWeight="500" onClick={this.addTechnologyOnTechnologyTempList}/>

            if(tempTechnologiesList.technologies.length === 0){
                pendingTechnologies = <span>Nothing to show</span>;
            }
            else {
                pendingTechnologies = tempTechnologiesList.technologies.map(technology => {
                    return (
                            <Technology 
                            technologyRemove={() => this.onTechnologyRemoveFromPendingClicked(technology.name)} 
                            isTechnologyEditClicked = {isTechnologyEditActive} 
                            key = {technology.name} 
                            technology = {technology.name}/>
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

        let technologies;

        if(user.isUpdateUserTitlesLoading){
            technologies = <Spinner width={20}/>;
        }
        else {
            if(user.technologies.length === 0){
                if(isTechnologyEditActive){
                    technologies = <Button value="Save Technologies" margin="5px 0px 5px 0px" fontWeight="500" onClick={this.onTechnologyEditClicked}/>
                }
                else{
                    technologies = <span>No technologies found, <Link to="#" onClick={this.onTechnologyEditClicked}>add technologies</Link></span>
                }
            }
            else {
                technologies = user.technologies.map(technology => {
    
                    let pendingRemove = false;
    
                    if(pendingRemovedTechnologies.indexOf(technology.technologyId) !== -1){
                        pendingRemove= true;
                    }
    
                    return (<Technology 
                        technologyRemove={() => this.onTechnologyRemoveFromUserClicked({
                                technologyId: technology.technologyId, 
                                name: technology.name
                            })} 
                        technologyRecycle={() => this.onTechnologyRecycleFromUserClicked({
                            technologyId: technology.technologyId, 
                            name: technology.name
                        })}
                        pendingRemove={pendingRemove}
                        isTechnologyEditClicked = {isTechnologyEditActive} 
                        key = {technology.technologyId} 
                        technology = {technology.name}/>)
                    
                })
            }
        }

        let profileContent = (<div id="account-profile">
                                <span>No profile found, <Link to="/edit-profile">setup your profile</Link>.</span>
                            </div>)

        if(user.profile.userId !== 0){
            profileContent = (<div id="account-profile">
                <Link to="/edit-profile" ><i className="fa fa-pencil account-edit" /></Link>
                <div id="account-profile-information">
                    <p><strong>Fullname: </strong>{`${user.profile.lastName}, ${user.profile.firstName} ${user.profile.middleName}`}</p>
                    <p><strong>Email Address: </strong>{user.profile.email}</p>
                    <p><strong>Created At: </strong>{user.profile.createdAt ? <ReactMoment tz="Asia/Manila" format="lll">{user.profile.createdAt}</ReactMoment> : ""} </p>
        <p><strong>Last Update: </strong><ReactMoment tz="Asia/Manila" format="lll">{user.profile.updatedAt ||  user.profile.createdAt}</ReactMoment> </p>
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
                    <div className="account-collapsible-header-div" style={titleCollasibleHeaderDiv}>
                        <p>Add or Remove a Title</p>
                    </div>
                    <div className="techtitle-div">
                        {titles}
                    </div>
                    <div className="account-collapsible-div" style={titleCollasibleDiv}>
                        <div className="account-input-div">
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
                        <div className="pending-techtitle-div">
                            <p className="pending-techtitle-header">Titles to be added:</p>
                            <div className="pending-techtitles">
                                {pendingTitles}
                            </div>
                        </div>
                    </div>
                </div>
                <div className="account-information">
                    <p className="account-label">Technologies</p>
                    {user.technologies.length === 0 ? "" : 
                        <i onClick={this.onTechnologyEditClicked} 
                        className={isTechnologyEditActive ? "fa fa-save account-edit": "fa fa-pencil account-edit"} 
                        title={isTechnologyEditActive ? "Save": "Edit"} 
                        />}
                    <div className="account-collapsible-header-div" style={technologyCollasibleHeaderDiv}>
                        <p>Add or Remove a Technology</p>
                    </div>
                    <div className="techtitle-div">
                        {technologies}
                    </div>
                    <div className="account-collapsible-div" style={technologyCollasibleDiv}>
                        <div className="account-input-div">
                            <TextField 
                                name="inputedTechnology.technology"
                                placeholder="Input Technology"
                                value={inputedTechnology.technology}
                                onChange={this.onChange}
                                width="70%"
                                margin="0px"
                                error= {inputedTechnology.error}
                            />
                            {technologyAddButton}
                        </div>
                        <div className="pending-techtitle-div">
                            <p className="pending-techtitle-header">Technologies to be added:</p>
                            <div className="pending-techtitles">
                                {pendingTechnologies}
                            </div>
                        </div>
                    </div>
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
    updateUserTechnologies: PropTypes.func.isRequired,
    getTitles: PropTypes.func.isRequired,
    getTechnologies: PropTypes.func.isRequired
}

const mapStateToProps = (state) => ({
    auth: state.auth,
    user: state.user,
    titles: state.titles,
    technologies: state.technologies
})

export default connect(mapStateToProps, {getUser, addOrUpdateProfile, getTitles, getTechnologies, updateUserTitles, updateUserTechnologies})(Account)
