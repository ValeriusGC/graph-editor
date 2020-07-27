Description:

	This simple gawk script allows to aggregate results for 'git log --numstat' command. Script 
	outputs 4 basic statistics grouped by author. 

		1. Number of line insertions.
		2. Number of line deletions.
		3. Number of files changed.
		4. Number of commits.

	Examples:

		a) Statistics for all users and all commits in repository.

			git log --numstat | gawk -f ~/gitstatistics/stats.awk

		b) Statistics for last month of commits, for all users.

			git log --numstat --since="1 month ago" | gawk -f ~/gitstatistics/stats.awk

		c) Statistics for all commits of user 'apis72'.

			git log --numstat --author=apis72 | gawk -f ~/gitstatistics/stats.awk

		d) Example of generated output.

			Author: Alexey Pisanko <apis72@gmail.com>
			Insertions: 136
			Deletions: 0
			Files changed: 1
			Commits: 1

Additional Information:

	Copyright © 2010, Alexey Pisanko (apis72@gmail.com) All rights reserved.

	License: GPL 3/LGPL 3

	Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF ANY KIND, 
	either express or implied. See the License for the specific language governing rights and limitations 
	under the License.

