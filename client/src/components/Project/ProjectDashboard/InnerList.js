import React, { Component } from "react";
import Ticket from "./Tickets/Ticket";

export default class InnerList extends Component {
  render() {
    return this.props.tasks.map((task, index) => (
      <Ticket key={task.id} ticket={task} index={index} />
    ));
  }
}
