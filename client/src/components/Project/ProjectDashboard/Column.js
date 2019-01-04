import React, { Component } from "react";
// import { Link } from "react-router-dom";
import Ticket from "./Tickets/Ticket";
import { Droppable } from "react-beautiful-dnd";
import styled from "styled-components";

const TaskList = styled.div``;

export default class Column extends Component {
  render() {
    // console.log("column information:  " + JSON.stringify(this.props.column));
    // console.log("tasks in column:  " + JSON.stringify(this.props.tasks));
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
              <TaskList ref={provided.innerRef} {...provided.droppableProps}>
                {this.props.tasks.map((task, index) => (
                  <Ticket key={task.id} ticket={task} index={index} />
                ))}
                {provided.placeholder}
              </TaskList>
            )}
          </Droppable>
        </div>
      </div>
    );
  }
}
