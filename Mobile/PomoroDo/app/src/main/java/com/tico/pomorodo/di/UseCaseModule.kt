package com.tico.pomorodo.di

import com.tico.pomorodo.domain.repository.AuthRepository
import com.tico.pomorodo.domain.repository.CalendarRepository
import com.tico.pomorodo.domain.repository.CategoryRepository
import com.tico.pomorodo.domain.repository.TimerRepository
import com.tico.pomorodo.domain.repository.TodoRepository
import com.tico.pomorodo.domain.repository.TokenRepository
import com.tico.pomorodo.domain.repository.UserRepository
import com.tico.pomorodo.domain.usecase.auth.ClearAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ClearIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.GetAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.GetIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.IsAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.JoinUseCase
import com.tico.pomorodo.domain.usecase.auth.LoginUseCase
import com.tico.pomorodo.domain.usecase.auth.ReissueTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveAccessTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveIdTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.SaveRefreshTokenUseCase
import com.tico.pomorodo.domain.usecase.auth.ValidateTokenUseCase
import com.tico.pomorodo.domain.usecase.calendar.GetCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.calendar.InsertCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.calendar.UpdateCalendarDateForMonthUseCase
import com.tico.pomorodo.domain.usecase.category.DecideCategoryInvitationUseCase
import com.tico.pomorodo.domain.usecase.category.DeleteCategoryUseCase
import com.tico.pomorodo.domain.usecase.category.GetAllCategoryUseCase
import com.tico.pomorodo.domain.usecase.category.GetCategoryInfoUseCase
import com.tico.pomorodo.domain.usecase.category.InsertCategoryUseCase
import com.tico.pomorodo.domain.usecase.category.OutCategoryUseCase
import com.tico.pomorodo.domain.usecase.category.UpdateCategoryInfoUseCase
import com.tico.pomorodo.domain.usecase.timer.GetConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.InsertConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.UpdateConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.CreateDailyTimerStatUseCase
import com.tico.pomorodo.domain.usecase.timer.GetDailyTimerDataUseCase
import com.tico.pomorodo.domain.usecase.timer.UpdateTargetFocusTimeUseCase
import com.tico.pomorodo.domain.usecase.todo.DeleteTodoUseCase
import com.tico.pomorodo.domain.usecase.todo.GetCategoryWithTodoItemsUseCase
import com.tico.pomorodo.domain.usecase.todo.InsertTodoUseCase
import com.tico.pomorodo.domain.usecase.todo.UpdateTodoUseCase
import com.tico.pomorodo.domain.usecase.user.GetMyUserIdUseCase
import com.tico.pomorodo.domain.usecase.user.GetUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Singleton
    @Provides
    fun provideSaveRefreshTokenUseCase(authRepository: AuthRepository): SaveRefreshTokenUseCase {
        return SaveRefreshTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSaveAccessTokenUseCase(authRepository: AuthRepository): SaveAccessTokenUseCase {
        return SaveAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetAccessTokenUseCase(authRepository: AuthRepository): GetAccessTokenUseCase {
        return GetAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideIsAccessTokenUseCase(authRepository: AuthRepository): IsAccessTokenUseCase {
        return IsAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideClearAccessTokenUseCase(authRepository: AuthRepository): ClearAccessTokenUseCase {
        return ClearAccessTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideSaveIdTokenUseCase(authRepository: AuthRepository): SaveIdTokenUseCase {
        return SaveIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideGetIdTokenUseCase(authRepository: AuthRepository): GetIdTokenUseCase {
        return GetIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideClearIdTokenUseCase(authRepository: AuthRepository): ClearIdTokenUseCase {
        return ClearIdTokenUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideJoinUseCase(authRepository: AuthRepository): JoinUseCase {
        return JoinUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Singleton
    @Provides
    fun provideValidateTokenUseCase(tokenRepository: TokenRepository): ValidateTokenUseCase {
        return ValidateTokenUseCase(tokenRepository)
    }

    @Singleton
    @Provides
    fun provideReissueTokenUseCase(tokenRepository: TokenRepository): ReissueTokenUseCase {
        return ReissueTokenUseCase(tokenRepository)
    }

    @Singleton
    @Provides
    fun provideInsertTodoUseCase(todoRepository: TodoRepository): InsertTodoUseCase {
        return InsertTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideGetAllCategoryUseCase(categoryRepository: CategoryRepository): GetAllCategoryUseCase {
        return GetAllCategoryUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideGetCategoryInfoUseCase(categoryRepository: CategoryRepository): GetCategoryInfoUseCase {
        return GetCategoryInfoUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideInsertCategoryUseCase(categoryRepository: CategoryRepository): InsertCategoryUseCase {
        return InsertCategoryUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideGetCategoryWithTodoItemsUseCase(todoRepository: TodoRepository): GetCategoryWithTodoItemsUseCase {
        return GetCategoryWithTodoItemsUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateCategoryInfoUseCase(categoryRepository: CategoryRepository): UpdateCategoryInfoUseCase {
        return UpdateCategoryInfoUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteCategoryUseCase(categoryRepository: CategoryRepository): DeleteCategoryUseCase {
        return DeleteCategoryUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideOutCategoryUseCase(categoryRepository: CategoryRepository): OutCategoryUseCase {
        return OutCategoryUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideDecideCategoryInvitationUseCase(categoryRepository: CategoryRepository): DecideCategoryInvitationUseCase {
        return DecideCategoryInvitationUseCase(categoryRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateTodoUseCase(todoRepository: TodoRepository): UpdateTodoUseCase {
        return UpdateTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteTodoUseCase(todoRepository: TodoRepository): DeleteTodoUseCase {
        return DeleteTodoUseCase(todoRepository)
    }

    @Singleton
    @Provides
    fun provideGetCalendarDateForMonthUseCase(calendarRepository: CalendarRepository): GetCalendarDateForMonthUseCase {
        return GetCalendarDateForMonthUseCase(calendarRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateCalendarDateForMonthUseCase(calendarRepository: CalendarRepository): UpdateCalendarDateForMonthUseCase {
        return UpdateCalendarDateForMonthUseCase(calendarRepository)
    }

    @Singleton
    @Provides
    fun provideInsertCalendarDateForMonthUseCase(calendarRepository: CalendarRepository): InsertCalendarDateForMonthUseCase {
        return InsertCalendarDateForMonthUseCase(calendarRepository)
    }

    @Singleton
    @Provides
    fun provideGetConcentrationGoalUseCase(timerRepository: TimerRepository): GetDailyTimerDataUseCase {
        return GetDailyTimerDataUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideUpdateTargetTimeUseCase(timerRepository: TimerRepository): UpdateTargetFocusTimeUseCase {
        return UpdateTargetFocusTimeUseCase(timerRepository)
    }

    @Singleton
    @Provides
    fun provideCreateDailyTimerStatUseCase(timerRepository: TimerRepository): CreateDailyTimerStatUseCase =
        CreateDailyTimerStatUseCase(timerRepository)

    @Singleton
    @Provides
    fun provideGetUserProfileUseCase(userRepository: UserRepository): GetUserProfileUseCase =
        GetUserProfileUseCase(userRepository)

    @Singleton
    @Provides
    fun provideGetMyUserIdUseCase(userRepository: UserRepository): GetMyUserIdUseCase =
        GetMyUserIdUseCase(userRepository)
}