package org.betonskm.orchestrator.configuration.transactionoutbox;

import com.gruelbox.transactionoutbox.TransactionOutbox;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class TransactionInbox {

  @Delegate
  private final TransactionOutbox transactionOutbox;
}
