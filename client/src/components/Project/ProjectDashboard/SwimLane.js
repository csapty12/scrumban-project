import React, { Component } from "react";
// import { Link } from "react-router-dom";
import { Droppable } from "react-beautiful-dnd";
import styled from "styled-components";
import InnerList from "./InnerList";

const TaskList = styled.div``;

export default class SwimLane extends Component {
  render() {
    return (
      <div className="card--content col-10 col-lg-3">
        <h4 className="display-5 text-center title-backlog__border">
          {this.props.swimLane.title}{" "}
          <span style={{ fontSize: 11 }}>({this.props.tickets.length})</span>
        </h4>
        <div className="card-vertical-scroll-enabled">
          <a href="#">
            <div className="card text-center">
              <div className="card-header">Add Ticket &#x2b;</div>
            </div>
          </a>
          <TaskList>
            <InnerList tickets={this.props.tickets} />
          </TaskList>
          {/*<Droppable droppableId={this.props.column.id}>
            {provided => (
              <TaskList ref={provided.innerRef} {...provided.droppableProps}>
                <InnerList tasks={this.props.tasks} />
                {provided.placeholder}
              </TaskList>
            )}
            </Droppable>*/}
        </div>
      </div>
    );
  }
}
