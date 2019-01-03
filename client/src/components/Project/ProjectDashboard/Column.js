import React, { Component } from "react";
// import { Link } from "react-router-dom";
import Ticket from "./Tickets/Ticket";
import { Droppable } from "react-beautiful-dnd";
export default class Column extends Component {
  render() {
    console.log("tasks:  " + this.props.tasks);
    return (
      <div className="card--content col-10 col-lg-3">
        <div className="card-vertical-scroll-enabled">
          <h4 className="display-5 text-center title-backlog__border">
            {this.props.column.title}
          </h4>
          <a href="#">
            <div className="card text-center">
              <div className="card-header">Add Ticket &#x2b;</div>
            </div>
          </a>
          <Droppable droppableId={this.props.column.id}>
            {provided => (
              <div
                innerRef={provided.innerRef}
                ref={provided.innerRef}
                {...provided.droppableProps}
              >
                {this.props.tasks.map((task, index) => (
                  <Ticket ticket={task} key={task.id} index={index} />
                ))}
                {provided.placeholder}
              </div>
            )}
          </Droppable>
        </div>
      </div>
    );
  }
}
