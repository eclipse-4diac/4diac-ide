# Contributing to 4diac IDE

Thanks for your interest in this project.

[Eclipse 4diac](https://eclipse.dev/4diac) is a Open Source Framework for Industrial Automation & Control. 
It is a reference implementation for the IEC 61499 standard. 
IEC 61499 defines a domain specific modeling language for developing distributed industrial control solutions.
IEC 61499 extends IEC 61131-3 by improving the encapsulation of software components for increased re-usability, providing a vendor independent format, and simplifying support for controller to controller communication.
Its distribution functionality and the inherent support for dynamic reconfiguration provide the required infrastructure for Industrie 4.0 and industrial IoT applications

### What is 4diac IDE

[4diac IDE](https://eclipse.dev/4diac/en_ide.php) is an extensible, IEC 61499 compliant engineering environment for distributed control applications. The modeled applications can be downloaded to distributed field devices according the means defined by the IEC 61499 standard. The hardware capability definition allows to model the control hardware and its interconnections through networks.
screenshot of the 4diac IDE engineering environment for distributed control systems showing the system explorer, the function block nework editor for applications, subapplications, and composite FBs, and an ECC editor modeling the state machine of basic function blocks
The 4diac IDE is based on the Eclipse framework, which allows an easy integration of other plug-ins to the 4diac IDE providing new or extended functionality.

## Setting up Your Eclipse and GitHub Account

Create an [Eclipse account](https://accounts.eclipse.org/) if you don't already have one. 
See the ["Eclipse Foundation Account" section](https://www.eclipse.org/projects/handbook/#contributing-account) in the Eclipse Committer Handbook.

All contributors, who are not committers on this Eclipse project, must electronically sign the [Eclipse Contributor Agreement (ECA)](https://www.eclipse.org/legal/ECA.php) via their [Eclipse account](https://accounts.eclipse.org/).
For more information, please see the [Eclipse Committer Handbook](https://www.eclipse.org/projects/handbook/#contributing).

To verify the Eclipse Contributor Agreement, GitHub contributions must use the  same email address like your [Eclipse account](https://accounts.eclipse.org/).
If your GitHub account was registered with a different address, you can [add your Eclipse email address to the account](https://github.com/settings/emails) instead.

Also add your GitHub account name to your [Eclipse account](https://accounts.eclipse.org/) to enable automated management of access rights for Eclipse project members. 
You can do this by logging into your Eclipse account, choosing ["Edit Profile"](https://accounts.eclipse.org/user/edit) and add your GitHub account name in the social media links section.

If you are a committer in any of the Eclipse projects, you should receive an email containing an invite to join 
that project's GitHub organisation within 2 hours. You should accept the invite to maintain committer status in the GitHub organisation.

It is also recommended to [add SSH public keys to your GitHub account](https://github.com/settings/keys).

## Creating a Development Environment


For compiling 4diac IDE  you need at least a version 17 Java JRE. 
Higher version JREs are supported, but may require additional Eclipse IDE configuration.

For developing you need latest [Eclipse IDE](https://eclipse.org/downloads/eclipse-packages/).
Use **Eclipse Modeling Tools** edition.

In order for all dependencies to resolve correctly, the following additional Eclipse packages are required:

* M2E - Maven Integration for Eclipse
* M2E - PDE Integration
* XText Complete SDK

You can install these packages using your Eclipse IDE by selecting Help -> Install New Software (Eclipse Repository).

A detailed description on how to setup your build environment and build 4diac IDE for the different platforms can be found in our [Building and Running 4diac IDE from Source](https://eclipse.dev/4diac/en_help.php?helppage=html/development/building4diac.html#buildFromSource).

## Recommended Workflow

The recommended way for contributions is to create a fork of the main project repository and to create changes only in that fork
(see [Fork and pull model](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/getting-started/about-collaborative-development-models#fork-and-pull-model) in the GitHub documentation).
Then you can create a pull request (PR) to have your changes merged from the fork into the main project repository.

The workflow can be separated into two parts:
1. [Creating and managing a fork](#creating-and-managing-a-fork)
2. [Creating a PR](#creating-a-pull-request) from the fork and specifics of that process in the `eclipse-4diac` organization

If you are familiar with how to create and manage a fork, you can [skip that part](#creating-a-pull-request).

### Creating and Managing a Fork

Use the _Fork_ button (top right) on the GitHub repository page and choose _Create a new fork_ to create a forked repository under your GitHub account.
Whenever changes are pushed to the main project repository, you can click _Sync fork_ in the forked repository to get the latest source code from the main project repository.

Then you have two options:

1. Clone the main project repository and add the forked repository as an additional remote
   
   _With this option, you can easily retrieve the most recent changes from the main project repository by simply pulling the `develop` branch, as your default remote repository will be the main project repository._

   Clone the main repository to your Eclipse workspace. The repository URL can be found by clicking the _<> Code_ button on the top right of the _<> Code_ tab within the GitHub page for the main project repository. When you clone the repository, it will automatically be the added as the "origin" remote to your local clone.

   You then have to add your fork as an additional remote to push your changes to a branch of that fork.
   To this end, add the forked repository as an additional remote called "fork" (or whatever you like):
   * Go to your fork on GitHub.
   * Click the _<> Code_ button on the top right of the _<> Code_ tab copy the URL .
   * Add the URL as the "fork" remote to your local clone.

   Whenever the `develop` branch of the main project repository changes, pulling the `develop` branch of your local clone will give you the current state. In case you have created a branch and committed changes to it, you can rebase it onto the updated `develop` branch.

2. Clone the forked repository and add the main repository as an additional remote

   _With this option, you can easily create new branches for committed changes in your fork, as the default remote repository will be your forked repository._

   Clone the forked repository to your Eclipse workspace you have to change the URL for the repository during the setup. The repository URL can be found by clicking the _<> Code_ button on the top right of the _<> Code_ tab within the GitHub page for the forked repository. When you clone the forked repository, it will automatically be added as the "develop" remote to your local clone.

   You then have to add the main project repository as an additional remote.
   To this end, add the main project repository as an additional remote called "upstream":
   * Go to the main project repository on GitHub.
   * Click the _<> Code_ button on the top right of the _<> Code_ tab copy the URL.
   * Add the URL as the "upstream" remote to your local clone.

   Whenever the `develop` branch of the main project repository changes, you have to synchronize the `develop` branch of your fork. Go to the GitHub page of your forked repository and click _Sync fork_. You can then pull the `develop` branch of your local clone to retrieve the current state. In case you have created a branch and committed changes to it, you can rebase it onto the updated `main` branch.

### Creating a Pull Request

When you have set up your fork of a repository that you want to contribute change to, and if you have prepared a local clone for it, it you can perform changes, commit them, and propose them via a pull request as follows.

1. Create a branch for the changes you want to make. Set up that branch starting from your local `develop` branch and set the remote to your forked repository.

2. Change code as needed to fix the issue.

3. Commit code changes to the local branch of your fork. Make multiple commits if necessary as history can be retained, but note that you will usually be asked to combine your changes to a single commit per pull request. So keep changes as small as possible and try to split them up.

4. Once the fix is ready, push the branch to your forked repository (not to the original upstream project repository).

5. Open the forked repository page in your browser, switch to the branch where the fix is developed. Click the _Contribute_ button to start the _Open Pull Request_ workflow.

6. On the new page verify the target branch is the correct one to merge your commits. It should be the `develop` branch of the main project repository. Also verify that the list of commits contains the changes you intend to merge. If everything is fine, click on _Create pull request_ button at the bottom right.

7. If you are a committer, you can add reviewers if you want certain people to review a change. If you leave this empty, anyone from the project team will review.

8. If you push more commits to the same branch in your fork, they automatically get added to the pull request (and trigger a new round of builds and reviews). You can also amend your previous commits and force push them to your branch.
    * Note that you will usually be asked to squash your commits to a single one before a PR will be merged (there is one possible exception to this, read further).

9. Reviewers can review the pull request on the GitHub website or fetch the PR using the _Fetch GitHub PR_ menu entry in the _Git Repositories_ view inside the Eclipse IDE.

10. Every PR is automatically verified to check that contributors have a valid Eclipse Contributor Agreement (ECA).

11. Once the PR is approved, it can be merged by a committer. Select what fits best given these criteria:
    * Rebase and Merge (retains all commits separately; useful when developing a feature consisting of multiple independent commits)
    * Squash and Merge (all commits in the PR get squashed into a single commit; useful to avoid separate "fix comments" commits)

12. After the PR is merged, the source branch used for the PR can be deleted. 

13. Your fork is now _out-of-date_ with the main (upstream) project repository. In case the "origin" remote of your local clone is the forked repository, you should get it back up to date. You can either use the _Sync Fork_ function on GitHub to update the fork and pull the `main` branch to your local clone, or you can update the fork via git locally:
    * Pull the latest changes from "upstream" (the main project repository) into your local repository. 
    * Push those changes from your local repository to the "origin" (your fork).
    If you return to your fork on GitHub, you will see that the main branch is _up-to-date_ with the main project repository.

18. Your fork is now ready for your next contribution. 

## Commit Message Recommendations

```
<issue title> #<issue number>

Short description/summary as to why this change is being made
  
Fixes https://github.com/<Full URL to issues/issue #>
```


  Example: 

```
Fixed NPE when opening the instance viewer of CFB instances #321

The root cause of this NPE was that in a recent model change the 
containment of the network for CFB and typed subapp instances was 
removed. This fix changed the model such that the containment was 
readded but to reduce performance impacts the derived flag was set.
The latter ensures that ECoreUtil.copy is not automatically copying 
the contained network.

Fixes https://github.com/eclipse-4diac/4diac-ide/issues/321 
```



## Contact

Contact the project developers via the project's "dev" list.

* https://dev.eclipse.org/mailman/listinfo/4diac-dev