package org.tmed.consultoriosback.email;

public record EmailDetails(
        String recipient,
        String msgBody,
        String subject,
        String attachment
) {


}
