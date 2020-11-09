---
layout: page
title: Tan Wei Liang's Project Portfolio Page
---

## Project: Fine$$e

### Overview

Fine$$e is a desktop finance tracker that allows tertiary students to better manage their finances, cultivating good financial habits such as saving. It is optimised for CLI users so that expenses and income can be tracked efficiently by typing in commands. It is written in Java, and has a GUI created with JavaFX.

### Summary of Contributions

* **New feature**: Overhauled the validation rules for data models.
  * What it does: Transforms the data model from the base Address Book project to one that is more suitable for the needs of Fine\$\$e.
  * Justification: Previously, the project contained data models such as `Name` and `Email`.
  In the initial stages of the project, to minimize the amount of development work needed we decided to map some of the existing models over to Fine\$\$e.
  However, the existing models had some existing validation rules which do not make sense in Fine\$\$e.
  * Highlights: To facilitate further operations on the data models, some of the underlying types have been changed from `String` to other Java data types.
  However, a layer of abstraction is maintained such that other components do not need to depend on the underlying data types.
  This includes converting the internal data from `public` to `private`, and using accessors to retrieve the data.

* **New feature**: Drafted the initial design for UI-dependent commands.
  * What it does: Allows the user to use commands with different behavior based on the current state of the user interface (in particular, which tab is currently active).
  * Justification: This streamlines the user experience, as they only need to remember a small set of intuitive commands.
  * Highlights: The existing commands were reused as much as possible, while complying with the standard OOP principles.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=wltan&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Set up the team [GitHub organization](https://github.com/AY2021S1-CS2103T-W16-3) and forked the tP repository.
  * Checked for code quality infractions in PRs.
  * Managed release `v1.3.1` and `v1.4` (2 releases) on GitHub.

* **Enhancements to existing features**:
  * Fixed obscure bugs. (Pull requests
  [#168](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/168),
  [#219](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/219),
  [#225](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/225),
  [#260](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/260))
  * Restricted users from using certain commands for Bookmark Transactions. (Pull request [#196](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/196))
  * Maintained code quality. (Pull requests [#284](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/284), [#304](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/304))

* **Documentation**:
  * User Guide:
    * Updated user guide for Add Expense, Add Income, Edit Expense, Edit Income
     commands for v1.3. (Pull request [#178](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/178))
    * Fixed some user guide bugs between v1.3 and v1.3.1. (Pull request [#206](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/206))
  * Developer Guide:
    * Elaborated on the `Model` component documentation, and added Pitest to the DevOps guide. (Pull request [#140](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/140))
    * Documented the implementation for add, edit, delete transaction features. (Pull request [#314](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/314))

* **Community**:
  * PRs reviewed (with non-trivial review comments):
  [#123](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/123),
  [#150](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/150),
  [#166](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/166),
  [#207](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/207),
  [#257](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/257),
  [#258](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/258).
  Check [here](https://github.com/AY2021S1-CS2103T-W16-3/tp/pulls?q=is%3Apr+reviewed-by%3Awltan) for a full up-to-date listing.
  * Contributions to Forum: [Forum link](https://github.com/nus-cs2103-AY2021S1/forum/issues?q=is%3Aissue+commenter%3Awltan)

* **Tools**:
  * Updated Gradle from `5.2.1` to `6.6.1`. (Pull request [#3](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/3))
  * Set up [PIT mutation testing](http://pitest.org/) as a test coverage tool, as an alternative to JaCoCo and CodeCov.
  (Pull request [#6](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/6))
  * Automated PIT mutation testing as a CI job that generates artifacts to speed up the PR review process. (Pull request [#296](https://github.com/AY2021S1-CS2103T-W16-3/tp/pull/296))

