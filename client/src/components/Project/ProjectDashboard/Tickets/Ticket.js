import React, { Component } from "react";
import { Link } from "react-router-dom";
import "../projectDashboard.css";
import { Draggable } from "react-beautiful-dnd";
import styled from "styled-components";

const Task = styled.div``;

class Ticket extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isHovering: false
    };
  }
  handleDelete = ticket => {
    console.log("ticket: " + JSON.stringify(ticket));
    console.log("props: " + JSON.stringify(this.props));
    this.props.deleteTicket(ticket);
  };
  handleMouseHover = () => {
    this.setState(this.toggleHoverState);
  };
  toggleHoverState = state => {
    return {
      isHovering: !state.isHovering
    };
  };
  render() {
    const { ticket } = this.props;

    const priority = ticket.priority;
    let priorityClass;
    if (priority === "LOW") {
      priorityClass = "priority-low";
    }
    if (priority === "MEDIUM") {
      priorityClass = "priority-medium";
    }
    if (priority === "HIGH") {
      priorityClass = "priority-high";
    }
    return (
      <Draggable
        draggableId={this.props.ticket.projectSequence}
        index={this.props.index}
      >
        {provided => (
          <Task
            className={`card ${priorityClass}`}
            {...provided.draggableProps}
            {...provided.dragHandleProps}
            ref={provided.innerRef}
          >
            <div
              className="card-body"
              onMouseEnter={this.handleMouseHover}
              onMouseLeave={this.handleMouseHover}
            >
              <div className="card-delete">
                <span>
                  <Link
                    to={`/updateProjectTicket/${ticket.projectIdentifier}/${
                      ticket.projectSequence
                    }`}
                  >
                    <h6 className="card-title">{ticket.projectSequence}</h6>
                  </Link>
                </span>

                {this.state.isHovering && (
                  <span
                    className="close-button"
                    onClick={this.handleDelete.bind(this, ticket)}
                  >
                    &times;
                  </span>
                )}
              </div>
              <div className="card-text ticket-text">{ticket.summary}</div>
            </div>
          </Task>
        )}
      </Draggable>
    );
  }
}
export default Ticket;
