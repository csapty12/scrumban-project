import React, { Component } from "react";
import { Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import InnerList from "./InnerList";
import Button from "@material-ui/core/Button";
import { withStyles } from "@material-ui/core/styles";
import CreateTicket from "./Tickets/CreateTicket";

const TaskList = styled.div`
  flex-grow: 1;
`;
const styles = theme => ({
  ticket: {
    backgroundColor: "#BFFAFF"
  }
});

class SwimLane extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isCreateTicketActive: false,
      projectIdentifier: this.props.projectIdentifier
    };
  }
  handleIsCreateTicketActive = () => {
    this.setState({ isCreateTicketActive: !this.state.isCreateTicketActive });
  };

  handleChange = event => {
    this.setState({
      [event.target.name]: event.target.value
    });
  };

  handleSubmit = ticket => {
    this.props.addTicketToSwimLane(ticket);
  };
  render() {
    return (
      <div className="card--content col-10 col-lg-3">
        <h4 className="display-5 text-center title-backlog__border">
          {this.props.swimLane.title}
          <span style={{ fontSize: 11 }}>({this.props.tickets.length})</span>
        </h4>

        <Button
          className="card-header"
          onClick={this.handleIsCreateTicketActive}
          aria-labelledby="form-dialog-title"
          disableRipple
          style={{
            backgroundColor: "#fff",
            border: "2px solid #e8e3e0",
            borderRadius: 0
          }}
        >
          Add Ticket &#x2b;
        </Button>

        <div className="card-vertical-scroll-enabled">
          <div className="text-center">
            {this.state.isCreateTicketActive && (
              <CreateTicket
                isActive={this.state.isCreateTicketActive}
                saveNewTicket={this.handleSubmit}
                swimLane={this.props.swimLane.title}
                projectIdentifier={this.state.projectIdentifier}
              />
            )}
          </div>
          <Droppable droppableId={this.props.swimLane.title}>
            {(provided, snapshot) => (
              <TaskList
                ref={provided.innerRef}
                {...provided.droppableProps}
                style={{
                  background: snapshot.isDraggingOver
                    ? "linear-gradient(#2196f3, 40%,transparent)"
                    : null,
                  transition: "0.3s"
                }}
              >
                <InnerList
                  tickets={this.props.tickets}
                  removeTicket={this.props.removeTicket}
                  swimLaneId={this.props.swimLane.title}
                  projectIdentifier={this.props.projectIdentifier}
                  onChange={this.handleChange}
                />
                {provided.placeholder}
              </TaskList>
            )}
          </Droppable>
        </div>
      </div>
    );
  }
}
export default withStyles(styles)(SwimLane);
