(window.webpackJsonp=window.webpackJsonp||[]).push([[0],{150:function(e,t,a){},177:function(e,t,a){e.exports=a(387)},182:function(e,t,a){},183:function(e,t,a){},184:function(e,t,a){},383:function(e,t,a){},387:function(e,t,a){"use strict";a.r(t);var n=a(0),r=a.n(n),i=a(27),o=a.n(i),c=(a(182),a(7)),l=a(8),s=a(10),d=a(9),m=a(11),p=(a(183),a(184),a(16)),u=a(75),h=a.n(u),f=a(76),j=a.n(f),b=a(77),g=a.n(b),v=a(163),E=a.n(v),y=a(15),O=a.n(y),k=a(165),w=a.n(k),C=a(164),D=a.n(C),S=a(48),N=a.n(S),T=a(47),I=a(36),P=a(29),x=a.n(P),A=a(31),L=a.n(A),F=a(33),B=a.n(F),R=a(32),M=a.n(R),W=function e(){var t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:"",a=arguments.length>1&&void 0!==arguments[1]?arguments[1]:"",n=arguments.length>2&&void 0!==arguments[2]?arguments[2]:"",r=arguments.length>3&&void 0!==arguments[3]?arguments[3]:"",i=arguments.length>4&&void 0!==arguments[4]?arguments[4]:"";Object(c.a)(this,e),this.id=t,this.projectName=a,this.projectIdentifier=n,this.description=r,this.createdAt=i},_=a(20),V=a.n(_),U=a(43),q=a.n(U),z=a(44),J=a.n(z),X=a(35),H=a.n(X);var Y,G=Object(p.withStyles)(function(e){return{appBar:{position:"relative",backgroundColor:"#2196F3"}}})(function(e){var t=e.classes;return r.a.createElement(q.a,{className:t.appBar},r.a.createElement(J.a,null,r.a.createElement(H.a,{variant:"h6",color:"inherit"},e.type," Project")))}),$=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleChange=function(e){var t=Object(I.a)({},a.state.project);t[e.target.name]=e.target.value,a.setState({project:t})},a.handleSubmit=function(e){e.preventDefault();var t=new W,n=a.state.project,r=n.projectName,i=n.description,o=n.createdAt;if("Create"===a.props.type)t.projectName=r,t.description=i,t.createdAt=o,t.projectIdentifier=a.slugifyProjectName(r),a.saveNewProject(t);else{var c=a.props.projectInfo,l=c.id,s=c.projectIdentifier,d=c.projectName,m=c.description,p=c.createdAt;t.id=l,t.projectIdentifier=s,t.projectName=a.state.project.projectName?a.state.project.projectName:d,t.description=a.state.project.description?a.state.project.description:m,t.createdAt=a.state.project.createdAt?a.state.project.createdAt:p,a.updateProjectInformation(t)}},a.state={isDialogActive:!0,project:new W,errors:{}},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"slugifyProjectName",value:function(e){return e.split(" ").join("-")}},{key:"updateProjectInformation",value:function(e){var t=this;V.a.patch("/api/project",e).then(function(e){t.props.updateProjectInfo(e.data),t.props.onClose()}).catch(function(e){console.log("json response : "+JSON.stringify(e.response.data)),t.setState({errors:{projectName:e.response.data.projectName,description:e.response.data.description}})})}},{key:"saveNewProject",value:function(e){var t=this;V.a.post("/api/project",e).then(function(e){t.props.updateAllProjects([].concat(Object(T.a)(t.props.allProjects),[e.data]))}).then(function(){t.props.onClose()}).catch(function(e){var a=e.response.data,n=a.description,r=a.projectName;t.setState({errors:{projectName:r,description:n}})})}},{key:"render",value:function(){var e=this.props,t=e.classes,a=e.projectInfo,n=this.state.errors;return r.a.createElement(L.a,{open:this.state.isDialogActive,onClose:this.props.onClose,"aria-labelledby":"form-dialog-title",scroll:"paper",fullWidth:!0,maxWidth:"md"},r.a.createElement(G,{type:this.props.type}),r.a.createElement("form",{onSubmit:this.handleSubmit},r.a.createElement(M.a,null,r.a.createElement(x.a,{autoFocus:!0,margin:"dense",id:"name",name:"projectName",label:"Project Name",type:"text",fullWidth:!0,onChange:this.handleChange,defaultValue:void 0===a?"":a.projectName}),n.projectName&&r.a.createElement("span",{className:t.error},n.projectName),r.a.createElement(x.a,{id:"standard-multiline-static",name:"description",label:"Project Description",multiline:!0,rows:"4",margin:"normal",fullWidth:!0,onChange:this.handleChange,defaultValue:void 0===a?"":a.description}),n.description&&r.a.createElement("span",{className:t.error},n.description)),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.props.onClose,color:"primary"},"Cancel"),r.a.createElement(O.a,{color:"primary",type:"submit"},this.props.type))))}}]),t}(n.Component),K=Object(p.withStyles)(function(e){return{error:{color:"red",fontSize:12}}})($),Q=a(73),Z=a.n(Q),ee=a(74),te=a.n(ee),ae=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleDeleteProjectActiveDialog=function(){a.setState({isDeleteDialogActive:!1})},a.handleDeleteProject=function(e){a.props.handleDeleteProject(e),a.handleDeleteProjectActiveDialog()},a.state={isDeleteDialogActive:!0},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){return r.a.createElement(L.a,{open:this.state.isDeleteDialogActive,onClose:this.handleDeleteProjectActiveDialog,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description"},r.a.createElement(Z.a,{id:"alert-dialog-title"},"Delete Project"),r.a.createElement(M.a,null,r.a.createElement(te.a,{id:"alert-dialog-description"},"Are you sure you want to delete:"," "+this.props.project.projectName)),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.handleDeleteProjectActiveDialog,color:"primary"},"No"),r.a.createElement(O.a,{onClick:this.handleDeleteProject.bind(this,this.props.project),color:"primary",autoFocus:!0},"Yes")))}}]),t}(n.Component),ne=Object(p.withStyles)(function(e){return{}})(ae),re=a(25),ie=a(162),oe="SET_CURRENT_USER",ce="SET_ACTIVE_PROJECT",le={user:{},validToken:!1},se=Object(re.c)({security:function(){var e,t,a=arguments.length>0&&void 0!==arguments[0]?arguments[0]:le,n=arguments.length>1?arguments[1]:void 0;switch(n.type){case oe:return Object(I.a)({},a,{validToken:(t=n.payload,!!t),user:n.payload});case ce:return Object(I.a)({},a,{activeProject:(e=n.payload,e.activeProject)});default:return a}}}),de={},me=[ie.a];window.navigator.userAgent.includes("Chrome")?Y=Object(re.e)(se,de,Object(re.d)(re.a.apply(void 0,me),window.__REDUX_DEVTOOLS_EXTENSION__&&window.__REDUX_DEVTOOLS_EXTENSION__())):(Y=Object(re.e)(se,de,Object(re.d)(re.a.apply(void 0,me))),console.log("not using chrome"));var pe=Y,ue=a(388),he=function(e){function t(e){var a;Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleOpenMenuClick=function(e){a.setState({anchorEl:e.currentTarget})},a.handleCloseMenuClick=function(e){a.setState({anchorEl:null})},a.handleDeleteProject=function(e){a.props.deleteProject(e)},a.handleDeleteProjectClick=function(e){a.setState({deleteProject:e.currentTarget})},a.handleUpdateProjectClick=function(e){a.setState({updateProject:e.currentTarget})},a.handleUpdateProjectClose=function(){a.handleCloseMenuClick(null),a.setState({updateProject:null})},a.updateProjectInfo=function(e){a.setState({project:e})},a.activateProject=function(e){console.log("am in here, with projectID: "+e),pe.dispatch({type:ce,payload:{activeProject:e}});var t=e;localStorage.setItem("activeProject",t)};var n=a.props.project;return a.state={anchorEl:null,deleteProject:null,updateProject:null,project:n},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this,t=this.props.project,a=this.state,i=a.anchorEl,o=a.deleteProject,c=a.updateProject,l=Boolean(i),s=Boolean(o),d=Boolean(c);return r.a.createElement("div",{className:"col-md-6 col-lg-4 item"},r.a.createElement(h.a,null,r.a.createElement(j.a,{action:r.a.createElement(n.Fragment,null,r.a.createElement(g.a,{"aria-label":"More","aria-owns":l?"long-menu":void 0,"aria-haspopup":"true",onClick:this.handleOpenMenuClick},r.a.createElement(E.a,null)),r.a.createElement(D.a,{id:"long-menu",anchorEl:i,open:l,onClose:this.handleCloseMenuClick},r.a.createElement(N.a,{key:"updateItem"},r.a.createElement("span",{onClick:this.handleUpdateProjectClick},"Update Project"),d&&r.a.createElement(K,{type:"Update",onClose:this.handleUpdateProjectClose,projectInfo:this.state.project,updateProjectInfo:this.updateProjectInfo})),r.a.createElement(N.a,{key:"deleteItem"},r.a.createElement("span",{onClick:this.handleDeleteProjectClick},"Delete Project"),s&&r.a.createElement(ne,{project:t,handleDeleteProject:this.handleDeleteProject})))),title:t.projectName,subheader:"created: "+t.createdAt}),r.a.createElement(w.a,null,r.a.createElement(ue.a,{to:"/dashboard/".concat(t.projectIdentifier)},r.a.createElement(O.a,{size:"small",color:"primary",onClick:function(){return e.activateProject(t.projectIdentifier)}},"Dashboard")))))}}]),t}(n.Component),fe=Object(p.withStyles)(function(e){return{}})(he),je=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleIsProjectDialogActive=function(){a.setState({isProjectDialogActive:!0})},a.handleClose=function(){a.setState({isProjectDialogActive:!1,errors:{},project:new W})},a.handleProjectDelete=function(e){var t=a.state.allProjects,n=e.projectIdentifier;V.a.delete("/api/project/".concat(n)).then(function(){var e=t.filter(function(e){return e.projectIdentifier!==n});a.setState({allProjects:e})})},a.updateAllProjects=function(e){a.setState({allProjects:e})},a.state={allProjects:[],project:new W,isProjectDialogActive:!1},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"componentDidMount",value:function(){var e=this;V.a.get("/api/project").then(function(t){e.setState({allProjects:t.data})})}},{key:"render",value:function(){var e=this,t=this.props.classes,a=this.state.allProjects;return r.a.createElement("div",{className:"projects"},r.a.createElement("div",{className:"container"},r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-12"},r.a.createElement("h1",{className:"display-4 text-center"},"All Projects"),r.a.createElement("br",null),r.a.createElement(O.a,{variant:"contained",color:"secondary",onClick:this.handleIsProjectDialogActive,className:t.createButton,disableRipple:!0},"Create New project"),this.state.isProjectDialogActive&&r.a.createElement(K,{type:"Create",onClose:this.handleClose,allProjects:this.state.allProjects,updateAllProjects:this.updateAllProjects}),r.a.createElement("br",null),r.a.createElement("hr",null),r.a.createElement("section",{className:"gallery-block grid-gallery"},r.a.createElement("div",{className:"container"},r.a.createElement("div",{className:"row"},a.map(function(t){return r.a.createElement(fe,{key:t.id,project:t,deleteProject:e.handleProjectDelete})}))))))))}}]),t}(n.Component),be=Object(p.withStyles)(function(e){return{createButton:{background:"linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",border:0,borderRadius:3,boxShadow:"0 3px 5px 2px rgba(255, 105, 135, .3)",color:"white",height:35,padding:"0 30px"}}})(je),ge=function(e){function t(){return Object(c.a)(this,t),Object(s.a)(this,Object(d.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.props.classes;return r.a.createElement("div",null,r.a.createElement(q.a,{position:"static",className:e.navbar},r.a.createElement(J.a,null,r.a.createElement(H.a,{variant:"h6",color:"inherit",className:e.grow},"TrellBan"),r.a.createElement(ue.a,{to:"/dashboard"},r.a.createElement(O.a,{color:"inherit"},"Dashboard")),r.a.createElement(ue.a,{to:"/login"},r.a.createElement(O.a,{color:"inherit"},"Login")),r.a.createElement(ue.a,{to:"/register"},r.a.createElement(O.a,{color:"inherit"},"Register")))))}}]),t}(n.Component),ve=Object(p.withStyles)(function(e){return{navbar:{background:"linear-gradient(45deg, #2196F3 30%, #21CBF3 90%)",border:0,borderRadius:3,boxShadow:"0 3px 5px 2px rgba(33, 203, 243, .3)",color:"white",padding:"0 30px"},grow:{flexGrow:1}}})(ge),Ee=(a(346),a(149),a(347),a(391)),ye=a(390),Oe=(a(150),a(17)),ke=a(166),we=a(55),Ce=a(171),De=a(59),Se=a(5),Ne=a.n(Se),Te=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleEditTicket=function(){a.setState({readOnlyMode:!a.state.readOnlyMode,diableSaveButton:!a.state.diableSaveButton})},a.handleViewTicketDetailsDialog=function(){a.setState({openTicketDetails:!a.state.openTicketDetails,isDragDisabled:!a.state.isDragDisabled})},a.handleViewPriorityDropDown=function(){a.setState({isDropDownActive:!a.state.isDropDownActive})},a.handleChange=function(e){console.log("event: "+e.target.value),a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleSubmit=function(e){e.preventDefault();var t={id:a.props.ticket.id,summary:a.state.summary,acceptanceCriteria:a.state.acceptanceCriteria,complexity:a.state.complexity,priority:a.props.ticket.priority};a.props.handleUpdateTicket(t)},a.state={diableSaveButton:!0,readOnlyMode:!0,openTicketDetails:!1,isDragDisabled:!1,isDropDownActive:!1,summary:a.props.ticket.summary||"",acceptanceCriteria:a.props.ticket.acceptanceCriteria||"",complexity:a.props.ticket.complexity||"",priority:a.props.ticket.priority||""},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.props,t=e.classes,a=e.ticket;return r.a.createElement(n.Fragment,null,r.a.createElement("span",{onClick:this.handleViewTicketDetailsDialog},a.projectSequence),r.a.createElement(L.a,{open:this.state.openTicketDetails,onClose:this.handleViewTicketDetailsDialog,"aria-labelledby":"form-dialog-title",fullWidth:!0,maxWidth:"sm"},r.a.createElement(q.a,{className:t.appBar},r.a.createElement(J.a,null,r.a.createElement(H.a,{variant:"h6",color:"inherit",className:t.flex},a.projectIdentifier," / ",a.projectSequence),r.a.createElement(O.a,{color:"inherit",onClick:this.handleEditTicket},"Edit Ticket"))),r.a.createElement("form",{onSubmit:this.handleSubmit},r.a.createElement(M.a,null,r.a.createElement(x.a,{autoFocus:!0,margin:"dense",id:"name",name:"summary",label:"Ticket Summary",type:"text",fullWidth:!0,onChange:this.handleChange,defaultValue:a.summary,variant:"outlined",InputProps:{readOnly:this.state.readOnlyMode}}),r.a.createElement(x.a,{margin:"dense",id:"standard-multiline-static",name:"acceptanceCriteria",label:"Acceptance Criteria",multiline:!0,rows:"4",fullWidth:!0,onChange:this.handleChange,defaultValue:a.acceptanceCriteria,variant:"outlined",InputProps:{readOnly:this.state.readOnlyMode}}),r.a.createElement(x.a,{margin:"dense",id:"complexity",name:"complexity",label:"Ticket Complexity",type:"number",fullWidth:!0,onChange:this.handleChange,defaultValue:a.complexity,variant:"outlined",InputProps:{readOnly:this.state.readOnlyMode}})),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.handleViewTicketDetailsDialog,color:"primary"},"Close"),r.a.createElement(O.a,{onClick:this.handleViewTicketDetailsDialog,color:"primary",disabled:this.state.diableSaveButton,type:"submit"},"Save Changes")))))}}]),t}(n.Component),Ie=Object(p.withStyles)(function(e){return{appBar:{position:"relative",backgroundColor:"#2196F3"},flex:{flex:1},textField:{marginLeft:e.spacing.unit,marginRight:e.spacing.unit}}})(Te),Pe=a(167),xe=a.n(Pe),Ae=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleOpenDeleteTicketDialog=function(e){e.stopPropagation(),a.setState({isDeleteTicketDiaglogActive:!0})},a.handleCloseDeleteTicketDialog=function(){a.setState({isDeleteTicketDiaglogActive:!1})},a.handleDeleteTicket=function(e){a.handleCloseDeleteTicketDialog(),e.swimLane=a.props.swimLaneId,a.props.removeTicket(e)},a.state={isDeleteTicketDiaglogActive:!1},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.props,t=e.classes,a=e.ticket;return r.a.createElement(n.Fragment,null,r.a.createElement(g.a,{"aria-label":"Delete",size:"small",disableRipple:!0,onClick:this.handleOpenDeleteTicketDialog},r.a.createElement(xe.a,{size:"small",className:t.deletIcon})),r.a.createElement(L.a,{open:this.state.isDeleteTicketDiaglogActive,onClose:this.handleCloseDeleteTicketDialog,"aria-labelledby":"alert-dialog-title","aria-describedby":"alert-dialog-description"},r.a.createElement(Z.a,{id:"alert-dialog-title"},"Remove Ticket?"),r.a.createElement(M.a,null,r.a.createElement(te.a,{id:"alert-dialog-description"},"Are you sure you want to delete:"," ",r.a.createElement("b",null,this.props.ticket.projectSequence),"?")),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.handleCloseDeleteTicketDialog,color:"primary"},"No"),r.a.createElement(O.a,{onClick:this.handleDeleteTicket.bind(this,a),color:"primary"},"Yes"))))}}]),t}(n.Component),Le=Object(p.withStyles)(function(e){return{deletIcon:{fontSize:15}}})(Ae),Fe=function(e){function t(e){var a;Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleChange=function(e){a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleDelete=function(e){e.swimLane=a.props.swimLaneId,a.props.removeTicket(e)},a.handleClickOpen=function(e){e.stopPropagation(),a.setState({open:!0})},a.handleClose=function(){a.setState({open:!1})},a.handleEditTicket=function(){a.setState({readOnlyMode:!a.state.readOnlyMode,diableSaveButton:!a.state.diableSaveButton})},a.deleteButton=function(){var e=a.props.ticket;return r.a.createElement(Le,{ticket:e,removeTicket:a.handleDelete.bind(Object(De.a)(Object(De.a)(a)),e)})},a.handleChange=function(e){a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleUpdateTicket=function(e){e.ticketNumberPosition=a.props.ticket.ticketNumberPosition,V.a.patch("/dashboard/".concat(a.props.projectIdentifier,"/").concat(a.props.swimLaneId,"/").concat(e.id),e)},a.openProjectDetailsDialog=function(e){return r.a.createElement(Ie,{ticket:e,handleUpdateTicket:a.handleUpdateTicket})};var n=a.props.ticket;return a.state={open:!1,isHovering:!1,summary:n.summary,acceptanceCriteria:n.acceptanceCriteria,complexity:n.complexity,priority:n.priority,swimlaneId:a.props.swimLaneId},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this,t=this.props,a=t.ticket,n=t.classes,i=a.priority;return r.a.createElement(we.b,{index:this.props.index,draggableId:this.props.ticket.projectSequence,isDragDisabled:this.state.isDragDisabled},function(t,o){return r.a.createElement("div",Object.assign({},t.draggableProps,t.dragHandleProps,{ref:t.innerRef},t.droppableProps),r.a.createElement(h.a,{className:Ne()(n.cards,Object(Oe.a)({},n.high,"high"===i),Object(Oe.a)({},n.medium,"medium"===i),Object(Oe.a)({},n.low,"low"===i)),style:{backgroundColor:o.isDragging?"#F0F2EA":null}},r.a.createElement(j.a,{title:e.openProjectDetailsDialog(a),subheader:e.props.ticket.summary,action:e.deleteButton()})))})}}]),t}(n.Component),Be=Object(p.withStyles)(function(e){return{cards:{marginTop:5,marginBottom:5,borderRadius:1},high:{borderLeft:"3px solid red"},medium:{borderLeft:"3px solid #FF8C19"},low:{borderLeft:"3px solid #42CC00"},deletIcon:{fontSize:15}}})(Fe),Re=function(e){function t(){return Object(c.a)(this,t),Object(s.a)(this,Object(d.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(l.a)(t,[{key:"componentWillUpdate",value:function(e){return e.tickets!==this.props.tickets}},{key:"render",value:function(){var e=this;return this.props.tickets.map(function(t,a){return r.a.createElement(Be,{key:a,ticket:t,index:a,removeTicket:e.props.removeTicket,swimLaneId:e.props.swimLaneId,handleChange:e.props.onChange,projectIdentifier:e.props.projectIdentifier})})}}]),t}(n.Component),Me=a(102),We=a.n(Me),_e=a(101),Ve=a.n(_e),Ue=a(103),qe=a.n(Ue),ze=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleCreateTicketDialog=function(){a.setState({isCreateTicketDialogActive:!a.state.isCreateTicketDialogActive})},a.handleChange=function(e){console.log("event val: "+e.target.value),a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleViewDropDownMenu=function(){a.setState({isDropDownActive:!a.state.openDropDown})},a.handleDropDownOpen=function(){a.setState({isDropDownActive:!0})},a.handleDropDownClose=function(){a.setState({isDropDownActive:!1})},a.handleSubmit=function(e){e.preventDefault();var t={summary:a.state.summary,acceptanceCriteria:a.state.acceptanceCriteria,projectIdentifier:a.state.projectIdentifier,priority:a.state.priority,swimLane:a.props.swimLane,complexity:a.state.complexity};a.props.saveNewTicket(t)},a.state={isCreateTicketDialogActive:a.props.isActive,summary:"",acceptanceCriteria:"",projectIdentifier:a.props.projectIdentifier,priority:"",complexity:"",isDropDownActive:!1,swimlane:a.props.swimLane},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.props.classes;return r.a.createElement(L.a,{open:this.state.isCreateTicketDialogActive,onClose:this.handleCreateTicketDialog},r.a.createElement(q.a,{className:e.appBar},r.a.createElement(J.a,null,r.a.createElement(H.a,{variant:"h6",color:"inherit",className:e.flex},"Create New Ticket"))),r.a.createElement("form",{onSubmit:this.handleSubmit},r.a.createElement(M.a,null,r.a.createElement(x.a,{autoFocus:!0,margin:"dense",id:"name",name:"summary",label:"Summary",type:"text",fullWidth:!0,onChange:this.handleChange}),r.a.createElement(x.a,{id:"standard-multiline-static",name:"acceptanceCriteria",label:"Acceptance Criteria",multiline:!0,rows:"4",margin:"normal",fullWidth:!0,onChange:this.handleChange}),r.a.createElement(x.a,{margin:"dense",id:"complexity",name:"complexity",label:"Complexity (Optional)",type:"number",fullWidth:!0,onChange:this.handleChange}),r.a.createElement(We.a,{fullWidth:!0},r.a.createElement(Ve.a,{htmlFor:"demo-controlled-open-select"},"Priority (Optional)"),r.a.createElement(qe.a,{open:this.state.isDropDownActive,onClose:this.handleDropDownClose,onOpen:this.handleDropDownOpen,value:this.state.priority,onChange:this.handleChange,inputProps:{name:"priority",id:"demo-controlled-open-select"}},r.a.createElement(N.a,{value:"low"},"Low"),r.a.createElement(N.a,{value:"medium"},"Medium"),r.a.createElement(N.a,{value:"high"},"High")))),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.handleCreateTicketDialog,color:"primary"},"Cancel"),r.a.createElement(O.a,{onClick:this.handleCreateTicketDialog,color:"primary",type:"submit"},"Create"))))}}]),t}(n.Component),Je=Object(p.withStyles)(function(e){return{appBar:{position:"relative",backgroundColor:"#2196F3"},flex:{flex:1},textField:{marginLeft:e.spacing.unit,marginRight:e.spacing.unit}}})(ze);function Xe(){var e=Object(ke.a)(["\n  flex-grow: 1;\n"]);return Xe=function(){return e},e}var He=Ce.a.div(Xe()),Ye=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleIsCreateTicketActive=function(){a.setState({isCreateTicketActive:!a.state.isCreateTicketActive})},a.handleChange=function(e){a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleSubmit=function(e){a.props.addTicketToSwimLane(e)},a.state={isCreateTicketActive:!1,projectIdentifier:a.props.projectIdentifier},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this;return r.a.createElement("div",{className:"card--content col-10 col-lg-3"},r.a.createElement("h4",{className:"display-5 text-center title-backlog__border"},this.props.swimLane.title,r.a.createElement("span",{style:{fontSize:11}},"(",this.props.tickets.length,")")),r.a.createElement(O.a,{className:"card-header",onClick:this.handleIsCreateTicketActive,"aria-labelledby":"form-dialog-title",disableRipple:!0,style:{backgroundColor:"#fff",border:"2px solid #e8e3e0",borderRadius:0}},"Add Ticket +"),r.a.createElement("div",{className:"card-vertical-scroll-enabled"},r.a.createElement("div",{className:"text-center"},this.state.isCreateTicketActive&&r.a.createElement(Je,{isActive:this.state.isCreateTicketActive,saveNewTicket:this.handleSubmit,swimLane:this.props.swimLane.title,projectIdentifier:this.state.projectIdentifier})),r.a.createElement(we.c,{droppableId:this.props.swimLane.title},function(t,a){return r.a.createElement(He,Object.assign({ref:t.innerRef},t.droppableProps,{style:{background:a.isDraggingOver?"linear-gradient(#2196f3, 40%,transparent)":null,transition:"0.3s"}}),r.a.createElement(Re,{tickets:e.props.tickets,removeTicket:e.props.removeTicket,swimLaneId:e.props.swimLane.title,projectIdentifier:e.props.projectIdentifier,onChange:e.handleChange}),t.placeholder)})))}}]),t}(n.Component),Ge=Object(p.withStyles)(function(e){return{ticket:{backgroundColor:"#BFFAFF"}}})(Ye),$e=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleTicketDelete=function(e){var t=a.state,n=t.projectTickets,r=t.swimLanes,i=e.projectIdentifier,o=e.id;console.log("projectIdentifier: "+i),V.a.delete("/dashboard/".concat(i,"/").concat(o),{data:e}).then(function(){n[0][e.projectSequence]===e&&delete n[0][e.projectSequence];var t=Object(T.a)(r);t.forEach(function(t){var a=Object.keys(t);if(t[a].title===e.swimLane){var n=t[a].ticketIds.indexOf(e.projectSequence);t[a].ticketIds.splice(n,1)}}),a.setState({projectTickets:n,swimLanes:t})})},a.handleChange=function(e){a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleClickOpen=function(){a.setState({open:!0})},a.handleClose=function(){a.setState({open:!1,errors:{}})},a.handleSubmit=function(e){e.preventDefault();var t={name:a.state.name};V.a.post("/dashboard/".concat(a.state.projectIdentifier),t).then(function(e){a.setState({swimLanes:e.data.swimLanes,swimLaneOrder:e.data.swimLaneOrder})}).then(function(){return a.handleClose()}).catch(function(e){a.setState({errors:e.response.data})})},a.handleAddTicket=function(e){V.a.post("/dashboard/".concat(e.projectIdentifier,"/").concat(e.swimLane),e).then(function(t){var n=Object(T.a)(a.state.projectTickets);0===n.length?n.push(Object(Oe.a)({},Object.keys(t.data)[0].toString(),t.data[Object.keys(t.data)[0].toString()])):n[0][Object.keys(t.data)[0].toString()]=t.data[Object.keys(t.data)[0].toString()];var r=Object(T.a)(a.state.swimLanes),i=a.state.swimLanes.filter(function(t){return Object.keys(t).toString()===e.swimLane});i[0][e.swimLane].ticketIds.push(Object.keys(t.data)[0].toString());var o=r.indexOf(i[0]);r.splice(o,1),r.splice(o,0,i[0]),a.setState({projectTickets:n,swimLanes:r})})},a.onDragEnd=function(e){document.body.style.color="inherit";var t=e.destination,n=e.source,r=e.draggableId;if(t&&(t.droppableId!==n.droppableId||t.index!==n.index)){var i=a.state.swimLanes.filter(function(e){return Object.keys(e)[0]===n.droppableId?e:null}),o=a.state.swimLanes.filter(function(e){return Object.keys(e)[0]===t.droppableId?e:null});if(i[0]===o[0]){var c=i[0][n.droppableId],l=Array.from(c.ticketIds);l.splice(n.index,1),l.splice(t.index,0,r);var s=Object(I.a)({},c,{ticketIds:l}),d=Object(Oe.a)({},n.droppableId,s),m=Object(T.a)(a.state.swimLanes),p=m.indexOf(i[0]);m.splice(p,1),m.splice(p,0,d);var u=Object(I.a)({},a.state,{swimLanes:m});return a.setState(u),void a.updateSwimLaneIdOrder(s)}var h=Array.from(i[0][n.droppableId].ticketIds);h.splice(n.index,1);var f=Object(I.a)({},i[0][n.droppableId],{ticketIds:h}),j=Array.from(o[0][t.droppableId].ticketIds);j.splice(t.index,0,r);var b=Object(I.a)({},o[0][t.droppableId],{ticketIds:j}),g=Object(Oe.a)({},n.droppableId,f),v=Object(Oe.a)({},t.droppableId,b),E=Object(T.a)(a.state.swimLanes),y=E.indexOf(i[0]),O=E.indexOf(o[0]);E.splice(y,1),E.splice(y,0,g),E.splice(O,1),E.splice(O,0,v);var k=Object(I.a)({},a.state,{swimLanes:E});a.setState(k),a.udpateSwimLanesWithNewTickets(f,b)}},a.updateSwimLaneIdOrder=function(e){V.a.patch("/dashboard/".concat(a.state.projectIdentifier,"/").concat(e.title),e)},a.udpateSwimLanesWithNewTickets=function(e,t){var n=Array.of(e,t);V.a.patch("/dashboard/".concat(a.state.projectIdentifier),n)},a.state={projectTickets:[],swimLanes:[],swimLaneOrder:[],projectIdentifier:a.props.projectIdentifier,errors:{},open:!1,name:""},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"componentDidMount",value:function(){var e=this;console.log("my projet: "+this.state.projectIdentifier),V.a.get("/dashboard/".concat(this.state.projectIdentifier)).then(function(t){e.setState({projectTickets:t.data.tickets,swimLanes:t.data.swimLanes,swimLaneOrder:t.data.swimLaneOrder})}).catch(function(t){console.log("error:  "+JSON.stringify(t.response.data.project)),e.setState({errors:{projectError:t.response.data.project}})})}},{key:"render",value:function(){var e=this,t=this.props.classes,a=this.state.errors;return console.log("error from server: "+this.state.errors.projectError),r.a.createElement("div",{className:"container-fluid"},this.state.errors.projectError&&r.a.createElement("div",{className:"container alert alert-danger text-center"},this.state.errors.projectError),r.a.createElement("section",{className:"card-horizontal-scrollable-container"},r.a.createElement("div",{className:t.swimLane},r.a.createElement(we.a,{onDragEnd:this.onDragEnd,onDragStart:this.onDragStart},this.state.swimLaneOrder.map(function(t,a){var n=e.state.swimLanes[a][t],i=n.ticketIds.map(function(t){return e.state.projectTickets[0][t]});return r.a.createElement(Ge,{key:n.title,swimLane:n,tickets:i,projectIdentifier:e.props.projectIdentifier,removeTicket:e.handleTicketDelete,addTicketToSwimLane:e.handleAddTicket})})),r.a.createElement("div",{className:"col-10 col-lg-3"},!this.state.errors.projectError&&r.a.createElement(O.a,{variant:"outlined",color:"primary",onClick:this.handleClickOpen,disableRipple:!0},"New Swimlane +"),r.a.createElement(L.a,{open:this.state.open,onClose:this.handleClose,"aria-labelledby":"form-dialog-title"},r.a.createElement("form",{onSubmit:this.handleSubmit},r.a.createElement(M.a,null,r.a.createElement(x.a,{autoFocus:!0,margin:"dense",id:"name",name:"name",label:"Swimlane Name",type:"text",fullWidth:!0,onChange:this.handleChange}),a.name&&r.a.createElement("span",{className:t.error},a.name)),r.a.createElement(B.a,null,r.a.createElement(O.a,{onClick:this.handleClose,color:"primary"},"Cancel"),r.a.createElement(O.a,{color:"primary",type:"submit"},"Create"))))))))}}]),t}(n.Component),Ke=Object(p.withStyles)(function(e){return{createButton:{background:"linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)",border:0,borderRadius:3,boxShadow:"0 3px 5px 2px rgba(255, 105, 135, .3)",color:"white",height:48,padding:"0 30px"},swimLane:{display:"flex",width:"100%"},error:{color:"red",fontSize:12}}})($e),Qe=function(e){function t(){return Object(c.a)(this,t),Object(s.a)(this,Object(d.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=localStorage.getItem("activeProject");return console.log("my project ID: "+JSON.stringify(pe.getState())),r.a.createElement("div",{className:"project"},r.a.createElement("div",{className:"container"},r.a.createElement("div",{className:"row "},r.a.createElement("div",{className:"col-md-8 m-auto"},r.a.createElement("h5",{className:"display-4 text-center"},"Project Dashboard"),r.a.createElement("hr",null)))),r.a.createElement(Ke,{projectIdentifier:e}))}}]),t}(n.Component),Ze=(a(383),function(e){function t(){return Object(c.a)(this,t),Object(s.a)(this,Object(d.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){return r.a.createElement("div",{className:"container text-center"},r.a.createElement("h1",{className:"mt-5 text-white font-weight-light"},"Tellban"),r.a.createElement("p",{className:"lead text-white-50"},"Your Personal Project Management Tool"),r.a.createElement("hr",null),r.a.createElement("button",{type:"button",className:"btn btn-primary"},"Login"),r.a.createElement("button",{type:"button",className:"btn btn-success"},"Register"))}}]),t}(n.Component)),et=function(e){e?V.a.defaults.headers.common.Authorization=e:delete V.a.defaults.headers.common.Authorization},tt=a(79),at=a.n(tt),nt=a(389),rt=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).setRedirectToDashboard=function(){a.setState({redirectToDashboard:!0})},a.handleChange=function(e){console.log("pass: "+e.target.value),a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.handleSubmit=function(e){e.preventDefault();var t=a.state,n={email:t.email,password:t.password};console.log("new user: "+JSON.stringify(n)),V.a.post("/api/users/login",n).then(function(e){var t=e.data.token;localStorage.setItem("jwt",t),et(t);var n=at()(t);console.log("decoded : "+JSON.stringify(n)),pe.dispatch({type:oe,payload:n}),a.setRedirectToDashboard()}).catch(function(e){return a.setState({errors:e.response.data})})},a.state={email:"",password:"",errors:{},redirectToDashboard:!1},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.state.errors;return r.a.createElement("div",{className:"container py-5"},this.state.redirectToDashboard&&r.a.createElement(nt.a,{to:"/dashboard"}),r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-12"},r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-6 mx-auto"},r.a.createElement("div",{className:"card rounded-0"},r.a.createElement("div",{className:"card-header"},r.a.createElement("h3",{className:"mb-0"},"Login")),r.a.createElement("div",{className:"card-body"},r.a.createElement("form",{className:"form",autoComplete:"off",id:"formLogin",noValidate:"",onSubmit:this.handleSubmit,method:"POST"},r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"email"},"Email Address"),r.a.createElement("input",{type:"text",className:"form-control form-control-lg rounded-0",name:"email",id:"email",required:!0,onChange:this.handleChange})),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",null,"Password"),r.a.createElement("input",{type:"password",className:"form-control form-control-lg rounded-0",name:"password",id:"password",required:!0,autoComplete:"new-password",onChange:this.handleChange}),0!==Object.keys(e).length&&r.a.createElement("div",null,"Email address and password do not match")),r.a.createElement("button",{type:"submit",className:"btn btn-success btn-lg float-right",id:"btnLogin"},"Login")))))))))}}]),t}(n.Component),it=function(e){function t(e){var a;return Object(c.a)(this,t),(a=Object(s.a)(this,Object(d.a)(t).call(this,e))).handleChange=function(e){a.setState(Object(Oe.a)({},e.target.name,e.target.value))},a.setRedirect=function(){a.setState({redirectToLogin:!0})},a.handleSubmit=function(e){e.preventDefault();var t=a.state,n={email:t.email,firstName:t.firstName,lastName:t.lastName,password:t.password,confirmPassword:t.confirmPassword};console.log("new user: "+JSON.stringify(n)),V.a.post("/api/users/register",n).then(function(e){console.log("json here: "+JSON.stringify(e)),a.setRedirect()}).catch(function(e){console.log("json failed: "+JSON.stringify(e.response.data)),a.setState({errors:e.response.data})})},a.state={email:"",firstName:"",lastName:"",password:"",confirmPassword:"",errors:{},redirectToLogin:!1},a}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){var e=this.state.errors;return r.a.createElement("div",{className:"container py-5"},this.state.redirectToLogin&&r.a.createElement(nt.a,{to:"/login"}),r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-12"},r.a.createElement("div",{className:"row"},r.a.createElement("div",{className:"col-md-6 mx-auto"},r.a.createElement("div",{className:"card rounded-0"},r.a.createElement("div",{className:"card-header"},r.a.createElement("h3",{className:"mb-0"},"Register")),r.a.createElement("div",{className:"card-body"},r.a.createElement("form",{className:"form",autoComplete:"off",id:"formLogin",noValidate:"",onSubmit:this.handleSubmit},r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"firstName"},"First Name"),r.a.createElement("input",{type:"text",className:"form-control form-control-lg rounded-0",name:"firstName",id:"firstName",required:!0,onChange:this.handleChange,method:"POST"})),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"lastName"},"Last Name"),r.a.createElement("input",{type:"text",className:"form-control form-control-lg rounded-0",name:"lastName",id:"lastName",required:!0,onChange:this.handleChange})),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"email"},"Email Address"),r.a.createElement("input",{type:"email",className:"form-control form-control-lg rounded-0",name:"email",id:"email",required:!0,onChange:this.handleChange}),e.email&&r.a.createElement("div",null,e.email)),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"password"},"Password"),r.a.createElement("input",{type:"password",className:"form-control form-control-lg rounded-0",name:"password",id:"password",required:!0,onChange:this.handleChange}),e.password&&r.a.createElement("div",null,e.password)),r.a.createElement("div",{className:"form-group"},r.a.createElement("label",{htmlFor:"confirmPassword"},"Confirm Password"),r.a.createElement("input",{type:"password",className:"form-control form-control-lg rounded-0",name:"confirmPassword",id:"confirmPassword",required:!0,onChange:this.handleChange}),e.confirmPassword&&r.a.createElement("div",null,e.confirmPassword)),r.a.createElement("button",{type:"submit",className:"btn btn-success btn-lg float-right",id:"btnLogin"},"Register")))))))))}}]),t}(n.Component),ot=a(175),ct=function(e){var t=e.component,a=e.security,n=Object(ot.a)(e,["component","security"]);return r.a.createElement(ye.a,Object.assign({},n,{render:function(e){return!0===a?r.a.createElement(t,n):r.a.createElement(nt.a,{to:"/login"})}}))},lt=a(174),st=localStorage.getItem("jwt");if(st){et(st);var dt=at()(st);pe.dispatch({type:oe,payload:dt});var mt=Date.now()/1e3;dt.exp<mt&&(localStorage.removeItem("jwt"),et(!1),pe.dispatch({type:oe,payload:{}}),window.location.href="/")}var pt=function(e){function t(){return Object(c.a)(this,t),Object(s.a)(this,Object(d.a)(t).apply(this,arguments))}return Object(m.a)(t,e),Object(l.a)(t,[{key:"render",value:function(){return r.a.createElement(lt.a,{store:pe},r.a.createElement(Ee.a,null,r.a.createElement("div",{className:"App"},r.a.createElement(ve,null),r.a.createElement(ye.a,{exact:!0,path:"/",component:Ze}),r.a.createElement(ye.a,{exact:!0,path:"/login",component:rt}),r.a.createElement(ye.a,{exact:!0,path:"/register",component:it}),r.a.createElement(ct,{exact:!0,path:"/dashboard",component:be,security:!0}),r.a.createElement(ct,{exact:!0,path:"/dashboard/:projectIdentifier",component:Qe,security:!0}))))}}]),t}(n.Component);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));o.a.render(r.a.createElement(pt,null),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then(function(e){e.unregister()})}},[[177,1,2]]]);
//# sourceMappingURL=main.4bb9d809.chunk.js.map