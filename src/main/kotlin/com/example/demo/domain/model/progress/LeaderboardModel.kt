import com.example.demo.domain.model.auth.UserModel

data class LeaderboardUserModel(
    val index: Int,
    val points: Int,
    val user: UserModel
)


data class LeaderboardModel(
    val total: Int,
    val topTen: List<UserRank>
) {
    data class UserRank(
        val index: Int,
        val user: UserModel,
        val points: Int
    )
}
