import React, { Component, Fragment } from "react";
import { Link } from "react-router-dom";
import Ticket from "./Tickets/Ticket";
export default class Column extends Component {
  constructor(props) {
    super(props);
  }
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
          <div>
            {this.props.tasks.map(task => (
              <Ticket ticket={task} key={task.id} />
            ))}
          </div>
        </div>
      </div>
    );
  }
}
