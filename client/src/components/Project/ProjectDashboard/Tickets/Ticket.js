import React, { Component } from "react";

const Ticket = props => {
  const { ticket } = props;
  console.log("ticket :  " + JSON.stringify(ticket));
  return (
    <div className="card">
      <div className="card-body">
        <a href="#">
          <h6 className="card-title">{ticket.projectSequence}</h6>
        </a>
        <p className="card-text ticket-text">{ticket.summary}</p>
      </div>
    </div>
  );
};

export default Ticket;
