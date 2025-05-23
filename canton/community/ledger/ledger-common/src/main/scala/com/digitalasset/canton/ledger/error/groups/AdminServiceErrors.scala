// Copyright (c) 2025 Digital Asset (Switzerland) GmbH and/or its affiliates. All rights reserved.
// SPDX-License-Identifier: Apache-2.0

package com.digitalasset.canton.ledger.error.groups

import com.digitalasset.base.error.{
  DamlErrorWithDefiniteAnswer,
  ErrorCategory,
  ErrorCode,
  Explanation,
  Resolution,
}
import com.digitalasset.canton.ledger.error.ParticipantErrorGroup.LedgerApiErrorGroup.AdminServicesErrorGroup
import com.digitalasset.canton.logging.ErrorLoggingContext

@Explanation("Errors raised by Ledger API admin services.")
object AdminServiceErrors extends AdminServicesErrorGroup {

  val UserManagement: UserManagementServiceErrors.type =
    UserManagementServiceErrors
  val IdentityProviderConfig: IdentityProviderConfigServiceErrors.type =
    IdentityProviderConfigServiceErrors
  val PartyManagement: PartyManagementServiceErrors.type =
    PartyManagementServiceErrors

  @Explanation("This rejection is given when a new configuration is rejected.")
  @Resolution("Fetch newest configuration and/or retry.")
  object ConfigurationEntryRejected
      extends ErrorCode(
        id = "CONFIGURATION_ENTRY_REJECTED",
        ErrorCategory.InvalidGivenCurrentSystemStateOther,
      ) {

    final case class Reject(_message: String)(implicit
        loggingContext: ErrorLoggingContext
    ) extends DamlErrorWithDefiniteAnswer(
          cause = _message
        )

  }

  @Explanation("This rejection is given when a package upload is rejected.")
  @Resolution("Refer to the detailed message of the received error.")
  object PackageUploadRejected
      extends ErrorCode(
        id = "PACKAGE_UPLOAD_REJECTED",
        ErrorCategory.InvalidGivenCurrentSystemStateOther,
      ) {

    final case class Reject(_message: String)(implicit
        loggingContext: ErrorLoggingContext
    ) extends DamlErrorWithDefiniteAnswer(
          cause = _message
        )

  }

  @Explanation(
    "A cryptographic key used by the configured system is not valid"
  )
  @Resolution("Contact support.")
  object InternallyInvalidKey
      extends ErrorCode(
        id = "INTERNALLY_INVALID_KEY",
        ErrorCategory.SystemInternalAssumptionViolated, // Should have been caught by the participant
      ) {
    final case class Reject(_message: String)(implicit
        loggingContext: ErrorLoggingContext
    ) extends DamlErrorWithDefiniteAnswer(
          cause = _message
        )
  }

}
