import React, { Component } from "react";
import { Link } from "react-router-dom";

const Ticket = props => {
  const { ticket } = props;
  console.log("ticket :  " + JSON.stringify(ticket));
  return (
    <div className="card">
      <div className="card-body">
        <Link
          to={`/updateProjectTicket/${ticket.projectIdentifier}/${
            ticket.projectSequence
          }`}
        >
          <h6 className="card-title">{ticket.projectSequence}</h6>
        </Link>
        <p className="card-text ticket-text">{ticket.summary}</p>
      </div>
    </div>
  );
};

export default Ticket;
